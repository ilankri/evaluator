package models

trait Worker extends User {
  private[this] val _tasks = util.SynchronizedSet.empty[Task[Any, Any]]

  /* private[this] val _deliverables = */
  /*   util.SynchronizedSet.empty[Deliverable[Any, Any]] */

  def register(task: Task[Any, Any]): Unit = _tasks += task

  def unregister(task: Task[Any, Any]): Unit = _tasks -= task

  def submitDeliverable[Fmt](content: Fmt, task: Task[Any, Fmt]) = {
    val deliverable = new Deliverable(this, content, task)
    task.receive(deliverable)
    deliverable
  }

  def tasks = _tasks.toSet

  /* def deliverables = _tasks.toSet */
}
