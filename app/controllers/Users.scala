package controllers

import play.api.mvc._
import db._

class Users(cc: ControllerComponents, db: Db) extends AbstractController(cc) {
  def index(id: Long) =
    db.users.read(id) map (user =>
      Action { implicit request: Request[AnyContent] => Ok(user.toString) }
    ) getOrElse TODO
}
