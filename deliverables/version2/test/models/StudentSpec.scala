package models

import org.scalatest._
import org.scalatest.Matchers._

class StudentSpec extends FlatSpec {
  val student = new Student(0, "", "", "")

  "A student" should "be a user" in {
    student shouldBe a[User]
  }

  it should "be a worker" in {
    student shouldBe a[Worker]
  }

  it should "be an evaluator" in {
    student shouldBe a[Evaluator]
  }
}
