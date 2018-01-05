package models

trait Worker extends User {
  private[this] var _tasks = Set.empty[Task[Any, Any]]

  /* private[this] val _deliverables = */
  /*   util.SynchronizedSet.empty[Deliverable[Any, Any]] */

  def register(task: Task[Any, Any]): Unit = _tasks += task

  def unregister(task: Task[Any, Any]): Unit = _tasks -= task

  def submitDeliverable[Fmt](description: String, content: Fmt,
    task: Task[Any, Fmt]) = {
    val deliverable = new Deliverable(this, description, content, task)
    task.receive(deliverable)
    deliverable
  }

  def tasks = _tasks.toSet

  /* def deliverables = _tasks.toSet */
}
