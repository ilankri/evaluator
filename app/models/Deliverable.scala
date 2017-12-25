package models

class Deliverable[Fmt, TaskFmt](
    id: Long,
    author: User,
    date: java.time.LocalDateTime,
    content: Fmt,
    task: Task[TaskFmt, Fmt])
  extends Submission(id, author, date, content) {
  private var _evaluation: Option[Int] = None

  def evaluation: Option[Int] = _evaluation

  def evaluation_=(evaluation: Int) = _evaluation = Some(evaluation)
}
