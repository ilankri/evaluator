package models

import java.time.LocalDateTime
import java.util.concurrent.atomic.AtomicReference

class Deliverable[+Fmt <: AnyDeliveryFormat](
    author: User,
    description: String,
    content: Fmt,
    task: Task[AnyTaskFormat])
  extends Submission(Submission.nextId(), LocalDateTime.now(), author,
    description, content) {
  private[this] val _evaluation = new AtomicReference[Option[Float]](None)

  def evaluation: Option[Float] = _evaluation.get()

  def evaluation_=(evaluation: Float) = _evaluation.set(Some(evaluation))

  override def toString =
    s"Deliverable(id = $id, date = $date, authorId = ${author.id}, " +
      s"description = $description, content = $content, taskId = ${task.id})"
}
