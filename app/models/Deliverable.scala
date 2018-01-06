package models

import java.time.LocalDateTime

class Deliverable[+Fmt](
    author: User,
    description: String,
    content: Fmt,
    task: Task[Any])
  extends Submission(Submission.nextId(), LocalDateTime.now(), author,
    description, content) {
  private[this] var _evaluation: Option[(Int, Int)] = None

  def evaluation = _evaluation

  def evaluation_=(evaluation: (Int, Int)) = _evaluation = Some(evaluation)

  override def toString =
    s"Deliverable(id = $id, date = $date, authorId = ${author.id}, " +
      s"description = $description, content = $content, taskId = ${task.id})"
}
