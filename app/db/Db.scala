package db

import models._

abstract class Db(
    val users: CredentialTable[User],
    val tasks: Table[Task[Any, Any]])

object DefaultDb extends Db(CredentialTable.empty, Table.empty)

private[db] class MockDb(
    users: CredentialTable[User],
    tasks: Table[Task[Any, Any]])
  extends Db(users, tasks)

object MockDb {
  private def instructors(nbInstructors: Int) = {
    def instructor(i: Int) =
      User(s"instructor$i", s"instructor$i@mock.com", s"password$i",
        User.Instructor)

    for (i <- 1 to nbInstructors) yield instructor(i)
  }

  private def students(nbStudents: Int) = {
    def student(i: Int) =
      User(s"student$i", s"student$i@mock.com", s"password$i", User.Student)

    for (i <- 1 to nbStudents) yield student(i)
  }

  private def mcq(i: Int) = {
    import models.mcq._

    Mcq(Seq(
      Question(
        s"$i = ?",
        Seq(
          Choice(s"$i", true),
          Choice("-1", false)
        )
      ),
      Question(
        s"$i - $i = ?",
        Seq(
          Choice("0", true),
          Choice("1", false),
          Choice(s"$i - $i", true)
        )
      )
    ))
  }

  private def tasks(nbTasks: Int, instructors: Seq[User]) = {
    for (i <- 1 to nbTasks) yield new Task[Any, Any](
      instructors(i),
      s"Description $i",
      mcq(i),
      None,
      None
    )
  }

  def apply(n: Int) = {
    val instrs = instructors(n)
    new MockDb(
      CredentialTable(instrs ++ students(n): _*),
      Table(tasks(n, instrs): _*)
    )
  }
}
