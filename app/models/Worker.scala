package models

trait Worker extends User {
  private var tasks = Set.empty[Any]

  private var deliverables = Set.empty[Any]

  def register[ContentFmt, SolutionFmt](task: Task[ContentFmt, SolutionFmt]) =
    tasks += task

  def unregister[ContentFmt, SolutionFmt](task: Task[ContentFmt, SolutionFmt]) =
    tasks -= task

  def submit[Fmt, TaskFmt](deliverable: Deliverable[Fmt, TaskFmt]) =
    deliverables += deliverable
}
