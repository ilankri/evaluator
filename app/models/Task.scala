package models

import java.time.LocalDateTime

class Task[ContentFmt, SolutionFmt](
    author: User,
    content: ContentFmt,
    solution: Option[SolutionFmt],
    deadline: Option[LocalDateTime])
  extends Submission(Submission.nextId(), author, LocalDateTime.now(),
    content) {
  /* private[this] val workers = util.SynchronizedSet.empty[Worker] */

  private[this] val _deliverables =
    util.SynchronizedSet.empty[Deliverable[SolutionFmt, ContentFmt]]

  /* def addWorker(worker: Worker): Unit = workers += worker */

  /* def deleteWorker(worker: Worker): Unit = workers -= worker */

  def receive(deliverable: Deliverable[SolutionFmt, ContentFmt]) =
    _deliverables += deliverable

  def deliverables = _deliverables.toSet
}
