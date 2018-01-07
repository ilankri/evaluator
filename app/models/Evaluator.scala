package models

import java.time.LocalDateTime

trait Evaluator extends User {
  private[this] val _tasks = util.SynchronizedSet.empty[Task[Any]]

  def submitTask[A](description: String, content: A,
    deadline: Option[LocalDateTime] = None) = {
    val task = new Task(this, description, content, deadline)
    _tasks += task
    task
  }

  def evaluate[A](deliverable: Deliverable[A], evaluation: (Int, Int)) =
    deliverable.evaluation = evaluation

  def submittedTasks = _tasks.toTraversable
}
