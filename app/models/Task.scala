package models

import java.time.LocalDateTime

class Task[+Fmt](
    author: User,
    description: String,
    content: Fmt,
    val deadline: Option[LocalDateTime] = None)
  extends Submission(Submission.nextId(), LocalDateTime.now(), author,
    description, content) {
  private[this] val workers = util.SynchronizedSet.empty[Worker]

  /* private[this] var _deliverables = Set.empty[Deliverable[DeliveryFmt, Fmt]] */

  def register(worker: Worker) = workers += worker

  def unregister(worker: Worker) = workers -= worker

  private[this] def autocorrect[A](deliverable: Deliverable[A]) =
    content match {
      case content: AutoCorrectable => content.correct(deliverable.content)
      case _ => None
    }

  def member(worker: Worker) = workers contains worker

  def receive[A](deliverable: Deliverable[A]): Unit = {
    autocorrect(deliverable) foreach (deliverable.evaluation = _)
    /* _deliverables += deliverable */
  }

  /* def deliverables = _deliverables.toSet */

  override def toString =
    s"Task(id = $id, date = $date, authorId = ${author.id}, " +
      s"description = $description, content = $content, deadline = $deadline)"
}
