package controllers

import concurrent._

import play.api.mvc._

import db._

class UserRequest[A](
    request: MessagesRequest[A],
    val user: models.User)
  extends MessagesRequest[A](request, request.messagesApi) {
  def unwrap = request
}

class WorkerRequest[A](
    request: MessagesRequest[A],
    override val user: models.Worker)
  extends UserRequest[A](request, user)

class EvaluatorRequest[A](
    request: MessagesRequest[A],
    override val user: models.Evaluator)
  extends UserRequest[A](request, user)

class UserAction(
    db: Db,
    userIdKey: String,
    resultOnUnauthorized: Result,
    override val executionContext: ExecutionContext)
  extends ActionRefiner[MessagesRequest, UserRequest] {
  override def refine[A](request: MessagesRequest[A]) = Future.successful {
    val resOnUnauthorized: Either[Result, UserRequest[A]] =
      Left(resultOnUnauthorized)
    request.session.get(userIdKey).fold(resOnUnauthorized)(id =>
      db.readUser(id.toLong).fold(resOnUnauthorized)(user =>
        Right(new UserRequest(request, user))))
  }
}

class WorkerAction(
    resultOnUnauthorized: Result,
    override val executionContext: ExecutionContext)
  extends ActionRefiner[UserRequest, WorkerRequest] {
  override def refine[A](request: UserRequest[A]) = Future.successful {
    request.user match {
      case worker: models.Worker =>
        Right(new WorkerRequest(request, worker))
      case _ => Left(resultOnUnauthorized)
    }
  }
}

class EvaluatorAction(
    resultOnUnauthorized: Result,
    override val executionContext: ExecutionContext)
  extends ActionRefiner[UserRequest, EvaluatorRequest] {

  override def refine[A](request: UserRequest[A]) = Future.successful {
    request.user match {
      case evaluator: models.Evaluator =>
        Right(new EvaluatorRequest(request, evaluator))
      case _ => Left(resultOnUnauthorized)
    }
  }
}

abstract class UserAbstractController(cc: AppControllerComponents)
  extends MessagesAbstractController(cc) {
  private def permissionCheckAction(id: Long, ec: ExecutionContext) =
    new ActionFilter[UserRequest] {
      override def executionContext = ec

      override def filter[A](request: UserRequest[A]) = Future.successful {
        if (request.user.id != id) Some(AppResults.unauthorized) else
          None
      }
    }

  private def asWorkerAction =
    new WorkerAction(AppResults.unauthorized, cc.executionContext)

  private def asEvaluatorAction =
    new EvaluatorAction(AppResults.unauthorized, cc.executionContext)

  private def userAction =
    Action andThen new UserAction(
      cc.db,
      cc.userIdKey,
      Redirect(routes.AppController.signinForm),
      cc.executionContext
    )

  def workerAction = userAction andThen asWorkerAction

  def evaluatorAction = userAction andThen asEvaluatorAction

  def authUserAction(id: Long) =
    userAction andThen permissionCheckAction(id, cc.executionContext)

  def authWorkerAction(id: Long) = authUserAction(id) andThen asWorkerAction

  def authEvaluatorAction(id: Long) =
    authUserAction(id) andThen asEvaluatorAction
}
