package models

import org.scalatest._
import org.scalatest.Matchers._
import org.scalamock.scalatest.MockFactory

class UserSpec extends FlatSpec with MockFactory {
  val user = User("", "", "", User.Student)

  "A user" should "be identifiable" in {
    user shouldBe a[util.Identifiable]
  }

  it should "be authenticatable" in {
    user shouldBe a[util.Authenticatable]
  }
}
