package models

class Task[ContentFmt, SolutionFmt](
    id: Long,
    author: User,
    date: java.time.LocalDateTime,
    content: ContentFmt,
    solution: Option[SolutionFmt],
    deadline: Option[java.time.LocalDateTime])
  extends Submission(id, author, date, content) {
  private var workers = Set.empty[Worker]

  def addWorker(worker: Worker) = workers += worker

  def deleteWorker(worker: Worker) = workers -= worker
}
