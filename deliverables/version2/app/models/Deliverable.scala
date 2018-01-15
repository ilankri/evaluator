package models

import java.time.LocalDateTime
import java.util.concurrent.atomic.AtomicReference

/** A deliverable is a submission made by a worker for a given task.  */
class Deliverable[+Fmt <: AnyDeliveryFormat](
    author: User,
    description: String,
    content: Fmt,
    task: Task[AnyTaskFormat])
  extends Submission(Submission.nextId(), LocalDateTime.now(), author,
    description, content) {
  private[this] val _evaluation = new AtomicReference[Option[Float]](None)

  /**
    * Returns the evaluation of this deliverable.
    *
    * @return an option value containing the evaluation and `None` if
    * the deliverable is not evaluated yet.
    */
  def evaluation: Option[Float] = _evaluation.get()

  /** Sets the evaluation for this deliverable.  */
  def evaluation_=(evaluation: Float) = _evaluation.set(Some(evaluation))

  override def toString =
    s"Deliverable(id = $id, date = $date, authorId = ${author.id}, " +
      s"description = $description, content = $content, taskId = ${task.id})"
}
