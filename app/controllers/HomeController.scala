package controllers

import play.api.mvc._

import db._

/**
  * This controller creates an `Action` to handle HTTP requests to the
  * application's home page.
  */
class HomeController(cc: MessagesControllerComponents, db: Db)
  extends AuthMessagesAbstractController(cc) {

  /**
    * Create an Action to render an HTML page.
    *
    * The configuration in the `routes` file means that this method
    * will be called when the application receives a `GET` request with
    * a path of `/`.
    */
  def index = Auth { Action { Redirect(routes.Users.index) } }

  def signin = Action { implicit request =>
    SigninForm.form.bindFromRequest.fold(
      formWithErrors => BadRequest(views.html.signin(formWithErrors, false)),
      signinData => {
        val user = db.users.read(signinData.username, signinData.password)
        user.fold(BadRequest(views.html.signin(SigninForm.form, true)))(user =>
          Redirect(routes.Users.index)
            .withSession(
              userIdKey.displayName.getOrElse("") -> user.id.toString
            )
        )
      }
    )
  }

  def signupPage = Action { Ok(views.html.signup()) }

  def signup = TODO

  def signinPage = GenericAuth(
    Action { Redirect(routes.Users.index) },
    Action { implicit request =>
      Ok(views.html.signin(SigninForm.form, false))
    },
    userIdKey
  )

  /* def home = Action { implicit request: Request[AnyContent] => */
  /*   Ok(views.html.home()) */
  /* } */

  /* def cour1 = Action { implicit request: Request[AnyContent] => */
  /*   Ok(views.html.cour1()) */
  /* } */
}
