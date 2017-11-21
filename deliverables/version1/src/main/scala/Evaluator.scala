class Evaluator(_name: String) extends User(_name) {
  def submit[Format](content: Format) = new Task(this, content)
}
