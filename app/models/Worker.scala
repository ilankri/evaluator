package models

trait Worker extends User {
  private[this] val _tasks = util.SynchronizedSet.empty[Task[Any, Any]]

  private[this] val _deliverables =
    util.SynchronizedSet.empty[Deliverable[Any, Any]]

  def register(task: Task[Any, Any]): Unit = _tasks += task

  def unregister(task: Task[Any, Any]): Unit = _tasks -= task

  def submit(deliverable: Deliverable[Any, Any]): Unit =
    _deliverables += deliverable

  def tasks = _tasks.toSet

  def deliverables = _tasks.toSet
}
