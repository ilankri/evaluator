package models

trait Worker extends User {
  private[this] var _tasks = Set.empty[Long]

  /* private[this] val _deliverables = */
  /*   util.SynchronizedSet.empty[Deliverable[Any, Any]] */

  def register[A](task: Task[A]): Unit = _tasks += task.id

  def unregister[A](task: Task[A]): Unit = _tasks -= task.id

  def submitDeliverable[A, B](description: String, content: B,
    task: Task[A]) = {
    val deliverable = new Deliverable(this, description, content, task)
    task.receive(deliverable)
    deliverable
  }

  def tasks = _tasks.toTraversable

  /* def deliverables = _tasks.toSet */
}
