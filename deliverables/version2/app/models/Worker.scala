package models

trait Worker extends User {
  private[this] val _tasks = util.ConcurrentSet.empty[Task[AnyTaskFormat]]

  /* private[this] val _deliverables = */
  /*   util.SynchronizedSet.empty[Deliverable[Any, Any]] */

  def register(task: Task[AnyTaskFormat]) = {
    task register this
    _tasks += task
  }

  def unregister(task: Task[AnyTaskFormat]) = {
    task unregister this
    _tasks -= task
  }

  def submitDeliverable(description: String, content: AnyDeliveryFormat,
    task: Task[AnyTaskFormat]) = {
    val deliverable = new Deliverable(this, description, content, task)
    task receive deliverable
    deliverable
  }

  def tasks: Traversable[Task[AnyTaskFormat]] = _tasks.toSet

  /* def deliverables = _tasks.toSet */
}
