package controllers

import play.api.mvc._
import db._

class Users(cc: MessagesControllerComponents, db: Db)
  extends AuthMessagesAbstractController(cc) {
  def index = Auth {
    Action { request =>
      db.users.read(request.attrs(userIdKey)) map (user =>
        Ok(views.html.home(user))) getOrElse NotImplemented
    }
  }
}
