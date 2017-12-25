package models

trait Evaluator extends User {
  private var tasks = Set.empty[Any]

  def submit[ContentFmt, SolutionFmt](task: Task[ContentFmt, SolutionFmt]) =
    tasks += task

  def evaluate[Fmt, TaskFmt](
    deliverable: Deliverable[Fmt, TaskFmt],
    evaluation: Int) =
    deliverable.evaluation = evaluation
}
