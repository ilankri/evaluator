package models

import org.scalatest._
import org.scalatest.Matchers._

class InstructorSpec extends FlatSpec {
  val instructor = new Instructor(0, "", "", "")

  "A instructor" should "be a user" in {
    instructor shouldBe a[User]
  }

  it should "be an evaluator" in {
    instructor shouldBe a[Evaluator]
  }
}
