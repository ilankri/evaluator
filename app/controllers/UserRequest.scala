package controllers

import concurrent._

import play.api.mvc._

import db._

class UserRequest[A](
    request: MessagesRequest[A],
    val user: models.User)
  extends WrappedRequest[A](request) {
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
        Right(new WorkerRequest(request.unwrap, worker))
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
        Right(new EvaluatorRequest(request.unwrap, evaluator))
      case _ => Left(resultOnUnauthorized)
    }
  }
}

abstract class UserAbstractController(cc: AppControllerComponents)
  extends AppAbstractController(cc) {
  private def permissionCheckAction(id: Long, ec: ExecutionContext) =
    new ActionFilter[UserRequest] {
      override def executionContext = ec

      override def filter[A](request: UserRequest[A]) = Future.successful {
        if (request.user.id != id) Some(AppResults.unauthorized) else
          None
      }
    }

  def authUserAction(id: Long) =
    super.userAction andThen permissionCheckAction(id, cc.executionContext)

  def authWorkerAction(id: Long) = authUserAction(id) andThen workerRefiner

  def authEvaluatorAction(id: Long) = authUserAction(id) andThen evaluatorRefiner
}
