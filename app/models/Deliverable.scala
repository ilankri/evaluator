package models

import java.time.LocalDateTime
import java.util.concurrent.atomic.AtomicReference

class Deliverable[+Fmt, +TaskFmt](
    author: User,
    content: Fmt,
    task: Task[TaskFmt, Fmt])
  extends Submission(Submission.nextId(), author, LocalDateTime.now(),
    content) {
  private[this] val _evaluation = new AtomicReference[Option[Int]](None)

  def evaluation: Option[Int] = _evaluation.get()

  def evaluation_=(evaluation: Int) = _evaluation.set(Some(evaluation))
}
