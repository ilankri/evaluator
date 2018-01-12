package models

import org.scalatest._
import org.scalatest.Matchers._

class InstructorSpec extends FlatSpec {
  val instructor = Mock.instructor

  "An instructor" should "be a user" in {
    instructor shouldBe a[User]
  }

  it should "be an evaluator" in {
    instructor shouldBe an[Evaluator]
  }
}
