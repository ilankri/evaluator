package models

class Instructor(id: Long, name: String, email: String, password: String)
  extends User(id, name, email, password)
  with Evaluator
