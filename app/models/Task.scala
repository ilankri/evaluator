package models

import java.time.LocalDateTime

class Task[+ContentFmt, +SolutionFmt](
    author: User,
    content: ContentFmt,
    solution: Option[SolutionFmt],
    deadline: Option[LocalDateTime])
  extends Submission(Submission.nextId(), author, LocalDateTime.now(),
    content) {
  private[this] val workers = util.SynchronizedSet.empty[Worker]

  def addWorker(worker: Worker): Unit = workers += worker

  def deleteWorker(worker: Worker): Unit = workers -= worker
}
