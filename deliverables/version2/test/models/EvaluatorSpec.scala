package models

import org.scalatest._
import org.scalatest.Matchers._

class EvaluatorSpec extends FlatSpec {
  val evaluator = Mock.evaluator

  "An evaluator" should "be a user" in {
    evaluator shouldBe a[User]
  }

  it should "not have any submitted task initially" in {
    evaluator.submittedTasks shouldBe empty
  }

  it should "be able to submit a task" in {
    val description = Mock.description
    val task = evaluator.submitTask(description, Mock.taskFormat)

    task shouldBe a[Task[_]]
    task.author shouldBe evaluator
    task.description shouldBe description
    task.content shouldBe an[AnyTaskFormat]
  }

  it should "be able to evaluate a deliverable" in {
    val deliverable = Mock.deliverable
    deliverable.evaluation should not be 'defined
    evaluator.evaluate(deliverable, Mock.evaluation)
    deliverable.evaluation shouldBe 'defined
  }

}
