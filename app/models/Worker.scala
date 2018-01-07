package models

trait Worker extends User {
  private[this] val _tasks = util.SynchronizedSet.empty[Task[Any]]

  /* private[this] val _deliverables = */
  /*   util.SynchronizedSet.empty[Deliverable[Any, Any]] */

  def register[A](task: Task[A]) = {
    task register this
    _tasks += task
  }

  def unregister[A](task: Task[A]) = {
    task unregister this
    _tasks -= task
  }

  def submitDeliverable[A, B](description: String, content: B,
    task: Task[A]) = {
    val deliverable = new Deliverable(this, description, content, task)
    task.receive(deliverable)
    deliverable
  }

  def tasks = _tasks.toTraversable

  /* def deliverables = _tasks.toSet */
}
