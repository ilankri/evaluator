package controllers

import concurrent._

import play.api.libs.typedmap.TypedKey
import play.api.mvc._

case class GenericAuth[A](
    action: Action[A],
    onFailure: Action[A],
    userIdKey: TypedKey[Long])
  extends Action[A] {
  override def apply(request: Request[A]): Future[Result] = {
    request.session.get(userIdKey.displayName getOrElse "").fold(
      onFailure(request))(id =>
        action(request.addAttr(userIdKey, id.toLong)))
  }

  override def parser = action.parser

  override def executionContext = action.executionContext
}

abstract class AuthMessagesAbstractController(cc: MessagesControllerComponents)
  extends MessagesAbstractController(cc) {
  val userIdKey = TypedKey[Long]("userId")

  class Auth(action: Action[AnyContent])
    extends GenericAuth[AnyContent](
      action,
      Action { implicit request: MessagesRequest[AnyContent] =>
        Redirect(routes.HomeController.signinPage)
      },
      userIdKey
    )

  object Auth {
    def apply(action: Action[AnyContent]) = new Auth(action)
  }
}
