package controllers

import org.scalatestplus.play._
import org.scalatestplus.play.components._

import play.api.test._
import play.api.test.CSRFTokenHelper._
import play.api.test.Helpers._

/**
  * Add your spec here.
  * You can mock out a whole application including requests, plugins etc.
  *
  * For more information, see
  * https://www.playframework.com/documentation/latest/ScalaTestingWithScalaTest
  */
class AppControllerSpec extends PlaySpec with OneAppPerSuiteWithComponents {
  override val components = new AppComponents(context)

  val expectedStatus = SEE_OTHER
  val expectedContentType = None

  "AppController GET" should {

    "be a redirection from a new instance of controller" in {
      val controller = new AppController(components.controllerComponents)
      val home = controller.index.apply(FakeRequest(GET, "/").withCSRFToken)

      status(home) mustBe expectedStatus
      contentType(home) mustBe expectedContentType
    }

    "be a redirection from the application" in {
      val home = components.appController.index.apply(
        FakeRequest(GET, "/").withCSRFToken
      )

      status(home) mustBe expectedStatus
      contentType(home) mustBe expectedContentType
    }

    "be a redirection from the router" in {
      val request = FakeRequest(GET, "/").withCSRFToken
      val home = route(app, request).get

      status(home) mustBe expectedStatus
      contentType(home) mustBe expectedContentType
    }
  }
}
