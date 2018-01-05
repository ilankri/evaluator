package controllers

import play.api.mvc._

/**
  * This controller creates an `Action` to handle HTTP requests to the
  * application's home page.
  */
class HomeController(cc: UserControllerComponents)
  extends MessagesAbstractController(cc) {

  /**
    * Create an Action to render an HTML page.
    *
    * The configuration in the `routes` file means that this method
    * will be called when the application receives a `GET` request with
    * a path of `/`.
    */
  def index = Action { Redirect(routes.Users.home) }

  def signupPage = Action { Ok(views.html.signup()) }

  def signup = TODO

  def signinPage = Action { implicit request =>
    Ok(views.html.signin(SigninForm.form, false))
  }

  def signin = Action { implicit request =>
    SigninForm.form.bindFromRequest.fold(
      formWithErrors => BadRequest(views.html.signin(formWithErrors, false)),
      signinData => {
        val user = cc.db.users.read(signinData.username, signinData.password)
        user.fold(BadRequest(views.html.signin(SigninForm.form, true)))(user =>
          Redirect(routes.Users.home).withSession(
            request.session + (cc.userIdKey -> user.id.toString))
        )
      }
    )
  }

}
