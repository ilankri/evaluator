package models

class Submission[format](_id: Int, _author: User, _date: String, _content: format) {
  val id = _id
  val author = _author
  val date = _date
  val content = _content
  def get_id(): Int = { return id }
  def get_author(): User = { return author }
  def get_date(): String = { return date }
  def get_content(): format = { return content }
}
