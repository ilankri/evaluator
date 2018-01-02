package controllers

import play.api.mvc._

import db._

/**
  * This controller creates an `Action` to handle HTTP requests to the
  * application's home page.
  */
class HomeController(
    messagesAction: MessagesActionBuilder,
    cc: ControllerComponents,
    db: Db)
  extends AbstractController(cc) {

  /**
    * Create an Action to render an HTML page.
    *
    * The configuration in the `routes` file means that this method
    * will be called when the application receives a `GET` request with
    * a path of `/`.
    */
  def index = messagesAction { implicit request: MessagesRequest[AnyContent] =>
    val id = request.session.get("id")
    id.fold(Ok(views.html.signin(SigninForm.form, false)))(id =>
      Redirect(routes.Users.index(id.toLong))
    )
  }

  def signin = messagesAction { implicit request: MessagesRequest[AnyContent] =>
    SigninForm.form.bindFromRequest.fold(
      formWithErrors => BadRequest(views.html.signin(formWithErrors, false)),
      signinData => {
        val user = db.users.read(signinData.username, signinData.password)
        user.fold(BadRequest(views.html.signin(SigninForm.form, true)))(user =>
          Redirect(routes.Users.index(user.id))
            .withSession("id" -> user.id.toString)
        )
      }
    )
  }

  def signup = Action { implicit request: Request[AnyContent] =>
    Ok(views.html.signup())
  }

  def home = Action { implicit request: Request[AnyContent] =>
    Ok(views.html.home())
  }

  def cour1 = Action { implicit request: Request[AnyContent] =>
    Ok(views.html.cour1())
  }
}
