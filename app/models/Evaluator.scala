package models

import java.time.LocalDateTime

trait Evaluator extends User {
  private[this] var _tasks = Set.empty[Long]

  def submitTask[A](description: String, content: A,
    deadline: Option[LocalDateTime] = None) = {
    val task = new Task(this, description, content, deadline)
    _tasks += task.id
    task
  }

  def evaluate[A](deliverable: Deliverable[A], evaluation: (Int, Int)) =
    deliverable.evaluation = evaluation

  def submittedTasks = _tasks.toTraversable
}
