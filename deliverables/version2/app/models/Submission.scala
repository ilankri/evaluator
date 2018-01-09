package models

abstract class Submission[+Fmt](
    override val id: Long,
    val date: java.time.LocalDateTime,
    val author: User,
    val description: String,
    val content: Fmt)
  extends util.Identifiable

object Submission extends util.IdGenerator
