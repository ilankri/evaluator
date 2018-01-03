package models

class Student(id: Long, name: String, email: String, password: String)
  extends User(id, name, email, password)
  with Worker
  with Evaluator {
  override def toString = s"Student(id = $id, name = $name, email = $email)"
}
