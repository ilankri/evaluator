package models

import org.scalatest._
import org.scalatest.Matchers._
import org.scalamock.scalatest.MockFactory

class WorkerSpec extends FlatSpec with MockFactory {
  val worker: Worker = new User(0, "", "", "") with Worker
  val task = new Task(mock[User], "", Mcq(Seq.empty))

  "A worker" should "be a user" in {
    worker shouldBe a[User]
  }

  it should "not be registered for any task initially" in {
    worker.tasks shouldBe empty
  }

  it should "be able to register for a task" in {
    worker register task
    worker.tasks should have size 1
    task member worker shouldBe true
  }

  it should "be able to submit a deliverable" in {
    val deliverable = worker.submitDeliverable("", McqSolution(Seq.empty), task)

    deliverable shouldBe a[Deliverable[_]]
    deliverable.author shouldBe worker
    deliverable.description shouldBe empty
    deliverable.content shouldBe a[AnyDeliveryFormat]
  }

  it should "be able to unregister from a task" in {
    worker unregister task
    worker.tasks shouldBe empty
    task member worker shouldBe false
  }
}
