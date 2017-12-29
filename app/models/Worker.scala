package models

trait Worker extends User {
  private[this] val tasks = util.SynchronizedSet.empty[Any]

  private[this] val deliverables = util.SynchronizedSet.empty[Any]

  def register[ContentFmt, SolutionFmt](
    task: Task[ContentFmt, SolutionFmt]): Unit =
    tasks += task

  def unregister[ContentFmt, SolutionFmt](
    task: Task[ContentFmt, SolutionFmt]): Unit =
    tasks -= task

  def submit[Fmt, TaskFmt](deliverable: Deliverable[Fmt, TaskFmt]): Unit =
    deliverables += deliverable
}
