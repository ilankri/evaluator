package models

import java.time.LocalDateTime
import java.util.concurrent.atomic.AtomicReference

class Deliverable[+Fmt](
    author: User,
    description: String,
    content: Fmt,
    task: Task[Any])
  extends Submission(Submission.nextId(), LocalDateTime.now(), author,
    description, content) {
  private[this] val _evaluation = new AtomicReference[Option[(Int, Int)]](None)

  def evaluation: Option[(Int, Int)] = _evaluation.get()

  def evaluation_=(evaluation: (Int, Int)) = _evaluation.set(Some(evaluation))

  override def toString =
    s"Deliverable(id = $id, date = $date, authorId = ${author.id}, " +
      s"description = $description, content = $content, taskId = ${task.id})"
}
