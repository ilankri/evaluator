package models

trait Evaluator extends User {
  private[this] val _tasks = util.SynchronizedSet.empty[Task[Any, Any]]

  def submit(task: Task[Any, Any]): Unit = _tasks += task

  def evaluate(deliverable: Deliverable[Any, Any], evaluation: Int) =
    deliverable.evaluation = evaluation

  def submittedTasks = _tasks.toSet
}
