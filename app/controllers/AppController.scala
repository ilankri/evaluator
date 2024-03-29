package controllers

import play.api.mvc._

/**
  * This controller manages the login/logout and the account creation.
  */
class AppController(cc: AppControllerComponents)
  extends MessagesAbstractController(cc) {

  /** The index page is the sign-in one.  */
  def index = Action { Redirect(routes.AppController.signinForm) }

  /** Displays the form to create an account.  */
  def signupForm = Action { Ok(views.html.signup()) }

  def signup = TODO

  /** Displays the form which asks for user credentials.  */
  def signinForm = Action { implicit request =>
    Ok(views.html.signin(SigninForm.form)(request))
  }

  /**
    * Starts a new session if the credentials are OK, otherwise asks the
    * user to retry.
    */
  def signin = Action { implicit request =>
    SigninForm.form.bindFromRequest.fold(
      formWithErrors => BadRequest(views.html.signin(formWithErrors)),
      signinData => {
        val user = cc.db.readUser(signinData.username, signinData.password)
        user.fold(BadRequest(views.html.signin(SigninForm.form, true)))(user =>
          Redirect(routes.Users.read(user.id)).withSession(
            request.session + (cc.userIdKey -> user.id.toString))
        )
      }
    )
  }

  /** Ends the user session.  */
  def signout = Action { request =>
    Redirect(routes.AppController.signinForm).withSession(
      request.session - cc.userIdKey
    )
  }
}
