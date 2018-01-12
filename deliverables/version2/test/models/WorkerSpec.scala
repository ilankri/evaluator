package models

import org.scalatest._
import org.scalatest.Matchers._

class WorkerSpec extends FlatSpec {
  val worker = Mock.worker
  val task = Mock.task

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
    val description = Mock.description
    val deliverable =
      worker.submitDeliverable(description, Mock.deliveryFormat, task)

    deliverable shouldBe a[Deliverable[_]]
    deliverable.author should have('id(worker.id))
    deliverable.description shouldBe description
    deliverable.content shouldBe an[AnyDeliveryFormat]
  }

  it should "be able to unregister from a task" in {
    worker unregister task
    worker.tasks shouldBe empty
    task member worker shouldBe false
  }
}
