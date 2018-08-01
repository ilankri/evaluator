package models

import java.time.LocalDateTime

/** A task is a submission made by an evaluator.  */
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

  /** Registers the given worker to this task.  */
  def register(worker: Worker): Unit = workers += worker

  /** Unregisters the given worker from this task.  */
  def unregister(worker: Worker): Unit = workers -= worker

  private[this] def autocorrect(deliverable: Deliverable[AnyDeliveryFormat]) =
    content match {
      case content: AutoCorrectable => content.correct(deliverable.content)
      case _ => None
    }

  /** Checks whether the given worker is registered for this task.  */
  def member(worker: Worker) = workers contains worker

  /**
    * Accepts the given deliverable by updating the collection of
    * deliverables for this task.  It also auto-corrects the deliverable
    * when the auto-correction is available.
    */
  def receive(deliverable: Deliverable[AnyDeliveryFormat]): Unit = {
    autocorrect(deliverable) foreach deliverable.evaluation_=
    _deliverables += deliverable
  }

  /** Returns all the deliverables submitted for this task.  */
  def deliverables: Traversable[Deliverable[AnyDeliveryFormat]] =
    _deliverables.toSet

  /**
    * Returns the best evaluation of `worker` for this task.
    *
    * @return an option value containing the best evaluation and `None`
    * if there is no such evaluation(either because the worker has not
    * submitted a deliverable or the deliverable has not been evaluated
    * yet.
    */
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
