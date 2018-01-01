package models

abstract class User(
    override val id: Long,
    override val name: String,
    val email: String,
    password: String)
  extends util.Identifiable
  with util.Authenticatable {
  override def checkPassword(password: String) = password == this.password

  override def toString = s"User(id = $id, name = $name, email = $email)"
}

object User extends util.IdGenerator {
  sealed abstract class Role
  case object Student extends Role
  case object Instructor extends Role

  def apply(name: String, email: String, password: String, role: Role): User = {
    val id = nextId()

    role match {
      case Student => new Student(id, name, email, password)
      case Instructor => new Instructor(id, name, email, password)
    }
  }
}
