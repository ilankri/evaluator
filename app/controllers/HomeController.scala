package controllers

import play.api.mvc._

/**
  * This controller creates an `Action` to handle HTTP requests to the
  * application's home page.
  */
class HomeController(cc: AppControllerComponents)
  extends MessagesAbstractController(cc) {

  /**
    * Create an Action to render an HTML page.
    *
    * The configuration in the `routes` file means that this method
    * will be called when the application receives a `GET` request with
    * a path of `/`.
    */
  def index = Action { Redirect(routes.UserController.home) }

  def signupPage = Action { Ok(views.html.signup()) }

  def signup = TODO

  def cour2 = Action { implicit request: Request[AnyContent] =>

    Ok(views.html.cour2())

  }

  def signinPage = Action { implicit request =>
    request.session.get(cc.userIdKey).fold(
      Ok(views.html.signin(SigninForm.form, false)))(_ =>
        Redirect(routes.UserController.home))
  }

  def signin = Action { implicit request =>
    SigninForm.form.bindFromRequest.fold(
      formWithErrors => BadRequest(views.html.signin(formWithErrors, false)),
      signinData => {
        val user = cc.db.readUser(signinData.username, signinData.password)
        user.fold(BadRequest(views.html.signin(SigninForm.form, true)))(user =>
          Redirect(routes.UserController.home).withSession(
            request.session + (cc.userIdKey -> user.id.toString))
        )
      }
    )
  }
}
