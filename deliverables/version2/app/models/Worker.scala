package models

/**
  * A worker is a user with the ability to register/unregister from
  * a task and submit a deliverable for a task.
  */
trait Worker extends User {
  private[this] val _tasks = util.ConcurrentSet.empty[Task[AnyTaskFormat]]

  /** Registers the worker for the given task.  */
  def register(task: Task[AnyTaskFormat]): Unit = {
    task register this
    _tasks += task
  }

  /** Unregisters the worker from the given task.  */
  def unregister(task: Task[AnyTaskFormat]): Unit = {
    task unregister this
    _tasks -= task
  }

  /**
    * Submits a deliverable for the given task with the worker as
    * author.
    *
    * @return the submitted deliverable.
    */
  def submitDeliverable(description: String, content: AnyDeliveryFormat,
    task: Task[AnyTaskFormat]) = {
    val deliverable = new Deliverable(this, description, content, task)
    task receive deliverable
    deliverable
  }

  /** Returns the tasks the worker is registered for.  */
  def tasks: Traversable[Task[AnyTaskFormat]] = _tasks.toSet
}
