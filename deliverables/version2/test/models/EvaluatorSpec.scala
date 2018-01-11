package models

import org.scalatest._
import org.scalatest.Matchers._
import org.scalamock.scalatest.MockFactory

class EvaluatorSpec extends FlatSpec with MockFactory {
  val evaluator: Evaluator = new User(0, "", "", "") with Evaluator

  "An evaluator" should "be a user" in {
    evaluator shouldBe a[User]
  }

  it should "not have any submitted task initially" in {
    evaluator.submittedTasks shouldBe empty
  }

  it should "be able to evaluate a deliverable" in {
    val task = new Task(evaluator, "", Mcq(Seq.empty))
    val deliverable = new Deliverable(
      mock[User],
      "",
      McqSolution(Seq.empty),
      task
    )
    evaluator.evaluate(deliverable, 0f)
    deliverable.evaluation should be('defined)
  }

  it should "be able to submit a task" in {
    val task = evaluator.submitTask("", Mcq(Seq.empty))

    task shouldBe a[Task[_]]
    task.author shouldBe evaluator
    task.description shouldBe empty
    task.content shouldBe a[AnyTaskFormat]
  }
}
