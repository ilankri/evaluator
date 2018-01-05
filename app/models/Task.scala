package models

import java.time.LocalDateTime

class Task[ContentFmt, SolutionFmt](
    author: User,
    description: String,
    content: ContentFmt,
    correction: Option[SolutionFmt],
    val deadline: Option[LocalDateTime])
  extends Submission(Submission.nextId(), LocalDateTime.now(), author,
    description, content) {
  /* private[this] val workers = util.SynchronizedSet.empty[Worker] */

  private[this] var _deliverables =
    Set.empty[Deliverable[SolutionFmt, ContentFmt]]

  /* def addWorker(worker: Worker): Unit = workers += worker */

  /* def deleteWorker(worker: Worker): Unit = workers -= worker */

  private[this] def autocorrect(
    deliverable: Deliverable[SolutionFmt, ContentFmt]) =
    content match {
      case content: CanCorrect[SolutionFmt] =>
        Some(content.correct(deliverable.content))
      case _ => None
    }

  def receive(deliverable: Deliverable[SolutionFmt, ContentFmt]): Unit = {
    autocorrect(deliverable) foreach (deliverable.evaluation = _)
    _deliverables += deliverable
  }

  def deliverables = _deliverables.toSet

  override def toString =
    s"Task(id = $id, date = $date, author = $author" +
      s"description = $description, content = $content)"
}
