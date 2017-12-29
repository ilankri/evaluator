package models

trait Evaluator extends User {
  private[this] val tasks = util.SynchronizedSet.empty[Any]

  def submit[ContentFmt, SolutionFmt](
    task: Task[ContentFmt, SolutionFmt]): Unit =
    tasks += task

  def evaluate[Fmt, TaskFmt](
    deliverable: Deliverable[Fmt, TaskFmt],
    evaluation: Int) =
    deliverable.evaluation = evaluation
}
