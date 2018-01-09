package models

import java.time.LocalDateTime

trait Evaluator extends User {
  private[this] val _tasks = util.SynchronizedSet.empty[Task[AnyTaskFormat]]

  def submitTask(description: String, content: AnyTaskFormat,
    deadline: Option[LocalDateTime] = None) = {
    val task = new Task(this, description, content, deadline)
    _tasks += task
    task
  }

  def evaluate(
    deliverable: Deliverable[AnyDeliveryFormat],
    evaluation: Float) =
    deliverable.evaluation = evaluation

  def submittedTasks: Traversable[Task[AnyTaskFormat]] = _tasks.toSet
}
