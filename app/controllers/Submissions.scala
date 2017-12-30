package controllers

import play.api.mvc._
import db._

class Submissions(cc: ControllerComponents, db: Db)
  extends AbstractController(cc) {
  def readAll = Action { implicit request: Request[AnyContent] =>
    val submissions = db.submissions.readAll

    Ok(if (submissions.isEmpty) "No submission" else submissions.mkString("\n"))
  }

  def create = TODO
}
