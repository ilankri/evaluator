package models

abstract class Submission[+Fmt](
    _id: Long,
    author: User,
    date: java.time.LocalDateTime,
    content: Fmt)
  extends util.Identifiable {
  override val id = _id

  override def toString =
    s"Submission(id = $id, author = $author, date = $date, content = $content)"
}

object Submission extends util.IdGenerator
