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
  private[this] def instructors(nbInstructors: Int) = {
    def instructor(i: Int) =
      User(s"instructor$i", s"instructor$i@mock.com", s"password$i",
        User.Instructor)

    for (i <- 1 to nbInstructors) yield instructor(i)
  }

  private[this] def students(nbStudents: Int) = {
    def student(i: Int) =
      User(s"student$i", s"student$i@mock.com", s"password$i", User.Student)

    for (i <- 1 to nbStudents) yield student(i)
  }

  def apply(nbInstructors: Int, nbStudents: Int) =
    new MockDb(
      CredentialTable(instructors(nbStudents) ++ students(nbStudents): _*),
      Table.empty
    )
}
