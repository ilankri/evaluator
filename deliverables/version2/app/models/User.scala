package models

/**
  * A user of the application is defined by a unique identifier, a name,
  * an email address and a password.
  */
abstract class User(
    override val id: Long,
    override val name: String,
    val email: String,
    password: String)
  extends util.Identifiable
  with util.Authenticatable {
  override def checkPassword(password: String) = password == this.password
}

/** Object for construction of users.  */
object User extends util.IdGenerator {
  /** There are two types of users: students and instructors.  */
  sealed abstract class Status
  case object Student extends Status
  case object Instructor extends Status

  /**
    * Creates a user with given name, email address, password and role.
    */
  def apply(name: String, email: String, password: String,
    status: Status): User = {
    val id = nextId()

    status match {
      case Student => new Student(id, name, email, password)
      case Instructor => new Instructor(id, name, email, password)
    }
  }
}
