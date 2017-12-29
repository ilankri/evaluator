package models

abstract class User(_id: Long, name: String, email: String, password: String)
  extends util.Identifiable {
  override val id = _id
}
