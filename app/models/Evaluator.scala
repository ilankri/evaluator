package models

import java.time.LocalDateTime

trait Evaluator extends User {
  private[this] val _tasks = util.SynchronizedSet.empty[Task[Any, Any]]

  def submitTask(content: Any, solution: Option[Any] = None,
    deadline: Option[LocalDateTime] = None) = {
    val task = new Task(this, content, solution, deadline)
    _tasks += task
    task
  }

  def evaluate(deliverable: Deliverable[Any, Any], evaluation: Int) =
    deliverable.evaluation = evaluation

  def submittedTasks = _tasks.toSet
}
