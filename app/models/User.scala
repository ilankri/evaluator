package models
import java.util.concurrent.atomic.AtomicLong

private[models] abstract class User(
    override val id: Long,
    val name: String,
    val email: String,
    password: String)
  extends util.Identifiable {
  def checkPassword(password: String) = password == this.password
}

object User {
  private val nextId = new AtomicLong(1)

  sealed abstract class Role
  case object Student extends Role
  case object Instructor extends Role

  def apply(name: String, email: String, password: String,
    role: Role): User = {
    val id = nextId.getAndIncrement()

    role match {
      case Student => new Student(id, name, email, password)
      case Instructor => new Instructor(id, name, email, password)
    }
  }
}
