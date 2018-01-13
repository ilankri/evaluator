package models

import java.time.LocalDateTime

class Task[+Fmt <: AnyTaskFormat](
    author: User,
    description: String,
    content: Fmt,
    val deadline: Option[LocalDateTime] = None)
  extends Submission(Submission.nextId(), LocalDateTime.now(), author,
    description, content) {
  private[this] val workers = util.ConcurrentSet.empty[Worker]

  private[this] val _deliverables =
    util.ConcurrentSet.empty[Deliverable[AnyDeliveryFormat]]

  def register(worker: Worker) = workers += worker

  def unregister(worker: Worker) = workers -= worker

  private[this] def autocorrect(deliverable: Deliverable[AnyDeliveryFormat]) =
    content match {
      case content: AutoCorrectable => content.correct(deliverable.content)
      case _ => None
    }

  def member(worker: Worker) = workers contains worker

  def receive(deliverable: Deliverable[AnyDeliveryFormat]): Unit = {
    autocorrect(deliverable) foreach deliverable.evaluation_=
    _deliverables += deliverable
  }

  def deliverables: Traversable[Deliverable[AnyDeliveryFormat]] =
    _deliverables.toSet

  def bestEvaluation(worker: Worker): Option[Float] = {
    val results =
      for {
        deliverable <- deliverables
        if deliverable.author.id == worker.id && !deliverable.evaluation.isEmpty
      } yield deliverable.evaluation.get
    try Some(results.max) catch {
      case _: UnsupportedOperationException => None
    }
  }

  override def toString =
    s"Task(id = $id, date = $date, authorId = ${author.id}, " +
      s"description = $description, content = $content, deadline = $deadline)"
}
