package models.mcq

case class Mcq(questions: Seq[Question]) extends models.AutoCorrectable {
  override def correct(solution: Any) = {
    solution match {
      case solution: Solution =>
        val score =
          questions.zip(solution.choices) count {
            case (question, choices) => question.check(choices)
          }
        Some((score, questions.size))
      case _ => None
    }
  }
}

case class Question(label: String, choices: Seq[Choice]) {
  def check(choices: Set[Choice]) =
    this.choices.filter(_.right).toSet == choices
}

case class Choice(label: String, right: Boolean)

case class Solution(choices: Seq[Set[Choice]])
