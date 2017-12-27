package controllers

import play.api._
import play.api.mvc._

/**
  * This controller creates an `Action` to handle HTTP requests to the
  * application's home page.
  */
class HomeController(cc: ControllerComponents) extends AbstractController(cc) {

  /**
    * Create an Action to render an HTML page.
    *
    * The configuration in the `routes` file means that this method
    * will be called when the application receives a `GET` request with
    * a path of `/`.
    */
  def signin = Action { implicit request: Request[AnyContent] =>
    Ok(views.html.signin())
  }

  def signup = Action { implicit request: Request[AnyContent] =>
    Ok(views.html.signup())
  }
}
