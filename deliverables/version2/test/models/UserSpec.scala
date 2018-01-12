package models

import org.scalatest._
import org.scalatest.Matchers._

import util._

class UserSpec extends FlatSpec {
  val user = Mock.user

  "A user" should "be identifiable" in {
    user shouldBe an[Identifiable]
  }

  it should "be authenticatable" in {
    user shouldBe an[Authenticatable]
  }
}
