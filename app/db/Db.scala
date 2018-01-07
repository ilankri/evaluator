package db

import models._

abstract class Db(
    users: CredentialTable[User],
    tasks: Table[Task[Any]]) {
  def create(user: User) = users.create(user)

  def create[A](task: Task[A]) = tasks.create(task)

  def readUser(id: Long) = users.read(id)

  def readUser(name: String, password: String) = users.read(name, password)

  def readTask(id: Long) = tasks.read(id)

  def readAllTasks = tasks.readAll

  def deleteUser(id: Long) = users.delete(id)

  def deleteTask(id: Long) = tasks.delete(id)
}

object DefaultDb extends Db(CredentialTable.empty, Table.empty)

private[db] class MockDb(
    users: CredentialTable[User],
    tasks: Table[Task[Any]])
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

  private def mcq(i: Long) = {
    import models.mcq._

    assert(i >= 0)
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

  private def tasks(users: Seq[User]) = {
    def asEvalutor(user: User) = user match {
      case evaluator: Evaluator => Some(evaluator)
      case _ => None
    }

    val evaluators = (for (user <- users) yield asEvalutor(user).repr).flatten
    for (e <- evaluators) yield e.submitTask(s"Description ${e.id}", mcq(e.id))
  }

  def apply(n: Int) = {
    val users = instructors(n) ++ students(n)
    new MockDb(
      CredentialTable(users: _*),
      Table(tasks(users): _*)
    )
  }
}
