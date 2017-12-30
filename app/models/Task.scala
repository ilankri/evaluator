package models

class Task[+ContentFmt, +SolutionFmt](
    id: Long,
    author: User,
    date: java.time.LocalDateTime,
    content: ContentFmt,
    solution: Option[SolutionFmt],
    deadline: Option[java.time.LocalDateTime])
  extends Submission(id, author, date, content) {
  private[this] val workers = util.SynchronizedSet.empty[Worker]

  def addWorker(worker: Worker): Unit = workers += worker

  def deleteWorker(worker: Worker): Unit = workers -= worker
}
