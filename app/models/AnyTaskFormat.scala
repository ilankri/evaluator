package models

sealed abstract trait AnyTaskFormat

case class McqQuestion(label: String, choices: Seq[McqChoice]) {
  def check(choices: Seq[Boolean]) = this.choices.map(_.right) == choices
}

case class McqChoice(label: String, right: Boolean)

case class Mcq(questions: Seq[McqQuestion])
  extends AnyTaskFormat with models.AutoCorrectable {

  override def correct(solution: AnyDeliveryFormat) = {
    solution match {
      case solution: McqSolution =>
        val score =
          questions.zip(solution.choices) count {
            case (question, choices) => question.check(choices)
          }
        println(score)
        Some((score.toFloat / questions.size) * 100)
      case _ => None
    }
  }
}
