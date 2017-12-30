package models

import java.util.concurrent.atomic.AtomicReference

class Deliverable[+Fmt, +TaskFmt](
    id: Long,
    author: User,
    date: java.time.LocalDateTime,
    content: Fmt,
    task: Task[TaskFmt, Fmt])
  extends Submission(id, author, date, content) {
  private[this] val _evaluation = new AtomicReference[Option[Int]](None)

  def evaluation: Option[Int] = _evaluation.get()

  def evaluation_=(evaluation: Int) = _evaluation.set(Some(evaluation))
}
