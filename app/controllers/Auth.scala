package controllers

import concurrent._

import play.api.libs.typedmap.TypedKey
import play.api.mvc._

import db._

case class GenericAuth[A](
    action: Action[A],
    onFailure: Action[A],
    userKey: TypedKey[models.User],
    db: Db)
  extends Action[A] {
  override def apply(request: Request[A]): Future[Result] = {
    request.session.get("id").fold(onFailure(request))(id =>
      db.users.read(id.toLong).fold(onFailure(request))(user =>
        action(request.addAttr(userKey, user)))
    )
  }

  override def parser = action.parser

  override def executionContext = action.executionContext
}

abstract class AuthMessagesAbstractController(
    cc: MessagesControllerComponents,
    db: Db)
  extends MessagesAbstractController(cc) {
  val userKey = TypedKey[models.User]("user")

  class Auth(action: Action[AnyContent])
    extends GenericAuth[AnyContent](
      action,
      Action { implicit request: MessagesRequest[AnyContent] =>
        Redirect(routes.HomeController.signinPage)
      },
      userKey,
      db
    )

  object Auth {
    def apply(action: Action[AnyContent]) = new Auth(action)
  }
}
