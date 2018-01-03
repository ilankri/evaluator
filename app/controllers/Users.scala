package controllers

import play.api.mvc._
import db._

class Users(cc: MessagesControllerComponents, db: Db)
  extends AuthMessagesAbstractController(cc, db) {
  def index = Auth {
    Action { request => Ok(views.html.home(request.attrs(userKey))) }
  }
}
