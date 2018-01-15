package models

/**
  * Abstract class for all types of submission.  A submission is
  * parameterized by the format of its content.
  */
abstract class Submission[+Fmt](
    override val id: Long,
    val date: java.time.LocalDateTime,
    val author: User,
    val description: String,
    val content: Fmt)
  extends util.Identifiable

/**
  * The companion object is simply used to generate identifiers for
  * submissions.
  */
object Submission extends util.IdGenerator
