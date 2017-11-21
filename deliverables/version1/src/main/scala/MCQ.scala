case class MCQ (questions: Seq[Question]) {
  def add(question: Question) = MCQ(questions :+ question)

  def add(choice: Choice) = {
    val lastQ = questions.last
    val updatedLastQ = Question(lastQ.label, lastQ.choices :+ choice)

    MCQ(questions.updated(questions.length - 1, updatedLastQ))
  }
}
