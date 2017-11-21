abstract class Submission[Format](_author: User, _content: Format) {
  def author: User = _author

  def content: Format = _content

  def id: Int = Submission.freshId()
}

object Submission {
  private var count = 0

  def freshId() = {
    count += 1
    count
  }
}
