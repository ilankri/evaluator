package util

trait Authenticatable {
  def name: String
  def checkPassword(password: String): Boolean
}
