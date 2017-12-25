package models

abstract class Submission[Fmt](
    id: Long,
    author: User,
    date: java.time.LocalDateTime,
    content: Fmt)
