package controllers

import org.scalatestplus.play._
import org.scalatestplus.play.components._

import play.api.test._
import play.api.test.Helpers._

/**
  * Add your spec here.
  * You can mock out a whole application including requests, plugins etc.
  *
  * For more information, see https://www.playframework.com/documentation/latest/ScalaTestingWithScalaTest
  */
class HomeControllerSpec extends PlaySpec with OneAppPerSuiteWithComponents {
  override val components = new conf.Components(context)

  "HomeController GET" should {

    "render the index page from a new instance of controller" in {
      val controller = new HomeController(
        components.messagesAction,
        stubControllerComponents(),
        components.database)
      val home = controller.signin().apply(FakeRequest(GET, "/"))

      status(home) mustBe OK
      contentType(home) mustBe Some("text/html")
      contentAsString(home) must include("Sign in")
    }

    "render the index page from the application" in {
      val home = components.homeController.signin().apply(FakeRequest(GET, "/"))

      status(home) mustBe OK
      contentType(home) mustBe Some("text/html")
      contentAsString(home) must include("Sign in")
    }

    "render the index page from the router" in {
      val request = FakeRequest(GET, "/")
      val home = route(app, request).get

      status(home) mustBe OK
      contentType(home) mustBe Some("text/html")
      contentAsString(home) must include("Sign in")
    }
  }
}
