package db

import models._

abstract class Db {
  val users = Table.empty[User]
  val submissions = Table.empty[Submission[Any]]
}

object DefaultDb extends Db

object MockDb extends Db {
  private def students(n: Int) =
    (1 to n) map (i =>
      User(s"student$i", s"student$i@mock.com", s"password$i", User.Student)
    )

  private def instructors(n: Int) =
    (1 to n) map (i =>
      User(s"instructor$i", s"instructor$i@mock.com", s"password$i",
        User.Instructor)
    )

  override val users = Table(instructors(10) ++ students(10): _*)

  override val submissions = Table.empty[Submission[Any]]
}
