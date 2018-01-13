package models

/** An instructor is a user with the evaluator role.  */
class Instructor(id: Long, name: String, email: String, password: String)
  extends User(id, name, email, password)
  with Evaluator {
  override def toString = s"Instructor(id = $id, name = $name, " +
    s"email = $email, password = $password)"
}
