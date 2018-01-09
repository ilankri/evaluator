package controllers

import play.api.data._
import play.api.data.Forms._

object SigninForm {
  case class Data(username: String, password: String)

  val form = Form(
    mapping(
      "username" -> text,
      "password" -> text
    )(Data.apply)(Data.unapply)
  )
}
