package controllers

import concurrent._

import play.api.mvc._

class UserRequest[A](
    request: MessagesRequest[A],
    val user: models.User)
  extends MessagesRequest[A](request, request.messagesApi)

class WorkerRequest[A](
    request: MessagesRequest[A],
    override val user: models.Worker)
  extends UserRequest[A](request, user)

class EvaluatorRequest[A](
    request: MessagesRequest[A],
    override val user: models.Evaluator)
  extends UserRequest[A](request, user)

abstract class UserAbstractController(cc: AppControllerComponents)
  extends MessagesAbstractController(cc) {
  private def withUserAction =
    new ActionRefiner[MessagesRequest, UserRequest] {
      override def executionContext = cc.executionContext

      override def refine[A](request: MessagesRequest[A]) = Future.successful {
        val resOnUnauthorized: Either[Result, UserRequest[A]] =
          Left(Redirect(routes.AppController.signinForm))
        request.session.get(cc.userIdKey).fold(resOnUnauthorized)(id =>
          cc.db.readUser(id.toLong).fold(resOnUnauthorized)(user =>
            Right(new UserRequest(request, user))))
      }
    }

  private def asWorkerAction =
    new ActionRefiner[UserRequest, WorkerRequest] {
      override def executionContext = cc.executionContext

      override def refine[A](request: UserRequest[A]) = Future.successful {
        request.user match {
          case worker: models.Worker =>
            Right(new WorkerRequest(request, worker))
          case _ => Left(AppResults.unauthorized)
        }
      }
    }

  private def asEvaluatorAction =
    new ActionRefiner[UserRequest, EvaluatorRequest] {
      override def executionContext = cc.executionContext

      override def refine[A](request: UserRequest[A]) = Future.successful {
        request.user match {
          case evaluator: models.Evaluator =>
            Right(new EvaluatorRequest(request, evaluator))
          case _ => Left(AppResults.unauthorized)
        }
      }
    }

  private def permissionCheckAction(id: Long) =
    new ActionFilter[UserRequest] {
      override def executionContext = cc.executionContext

      override def filter[A](request: UserRequest[A]) = Future.successful {
        if (request.user.id != id) Some(AppResults.unauthorized) else
          None
      }
    }

  private def userAction = Action andThen withUserAction

  def workerAction = userAction andThen asWorkerAction

  def evaluatorAction = userAction andThen asEvaluatorAction

  def authUserAction(id: Long) = userAction andThen permissionCheckAction(id)

  def authWorkerAction(id: Long) = authUserAction(id) andThen asWorkerAction

  def authEvaluatorAction(id: Long) =
    authUserAction(id) andThen asEvaluatorAction
}
