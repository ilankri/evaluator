package models

import java.time.LocalDateTime

/**
  * An evaluator is a user with the ability to create a task and
  * evaluate a deliverable.
  */
trait Evaluator extends User {
  private[this] val _tasks = util.ConcurrentSet.empty[Task[AnyTaskFormat]]

  /**
    * Submits a task with the evaluator as author.
    *
    * @return the submitted task.
    */
  def submitTask(description: String, content: AnyTaskFormat,
    deadline: Option[LocalDateTime] = None) = {
    val task = new Task(this, description, content, deadline)
    _tasks += task
    task
  }

  /** Evaluates a deliverable.  */
  def evaluate(
    deliverable: Deliverable[AnyDeliveryFormat],
    evaluation: Float) =
    deliverable.evaluation = evaluation

  /** Returns the tasks owned by the evaluator.  */
  def submittedTasks: Traversable[Task[AnyTaskFormat]] = _tasks.toSet
}
