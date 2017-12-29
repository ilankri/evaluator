package models

abstract class Submission[Fmt](
    _id: Long,
    author: User,
    date: java.time.LocalDateTime,
    content: Fmt)
  extends util.Identifiable {
  override val id = _id
}
