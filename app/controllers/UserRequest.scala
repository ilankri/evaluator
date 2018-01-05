package controllers

import concurrent._

import play.api._
import play.api.mvc._

import db._

class UserRequest[A](
    request: MessagesRequest[A],
    val user: models.User)
  extends WrappedRequest[A](request)

class WorkerRequest[A](
    request: UserRequest[A],
    val worker: models.Worker)
  extends WrappedRequest[A](request)

class EvaluatorRequest[A](
    request: UserRequest[A],
    val evaluator: models.Evaluator)
  extends WrappedRequest[A](request)

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
      db.users.read(id.toLong).fold(resOnUnauthorized)(user =>
        Right(new UserRequest(request, user))))
  }
}

class WorkerAction(
    resultOnUnauthorized: Result,
    override val executionContext: ExecutionContext)
  extends ActionRefiner[UserRequest, WorkerRequest] {
  override def refine[A](request: UserRequest[A]) = Future.successful {
    request.user match {
      case worker: models.Worker => Right(new WorkerRequest(request, worker))
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

abstract class UserAbstractController(cc: UserControllerComponents)
  extends MessagesAbstractController(cc) {
  private val unauthorized = Unauthorized(<h1>Unauthorized</h1>).as(HTML)

  def userAction =
    Action andThen (
      new UserAction(cc.db, cc.userIdKey,
        Redirect(routes.HomeController.signinPage), cc.executionContext)
    )

  def workerAction =
    userAction andThen (new WorkerAction(unauthorized, cc.executionContext))

  def evaluatorAction =
    userAction andThen (new EvaluatorAction(unauthorized, cc.executionContext))
}

class UserControllerComponents(
    messagesActionBuilder: MessagesActionBuilder,
    actionBuilder: DefaultActionBuilder,
    parsers: PlayBodyParsers,
    messagesApi: i18n.MessagesApi,
    langs: i18n.Langs,
    fileMimeTypes: http.FileMimeTypes,
    executionContext: concurrent.ExecutionContext,
    val db: Db,
    val userIdKey: String)
  extends DefaultMessagesControllerComponents(
    messagesActionBuilder,
    actionBuilder, parsers, messagesApi, langs, fileMimeTypes, executionContext)
