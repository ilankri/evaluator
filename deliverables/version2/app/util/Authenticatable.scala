package util

/** An authenticatable object has a name and a password checker.  */
trait Authenticatable {
  /** Returns the name of the object.  */
  def name: String

  /** Checks if the given password is right.  */
  def checkPassword(password: String): Boolean
}
