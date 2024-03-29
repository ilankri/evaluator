package models

/** Any task format must extend this class.  */
sealed abstract class AnyTaskFormat

case class McqQuestion(label: String, choices: Seq[McqChoice]) {
  def check(choices: Seq[Boolean]) = this.choices.map(_.right) == choices
}

case class McqChoice(label: String, right: Boolean)

/** A MCQ is represented by a sequence of questions.  */
case class Mcq(questions: Seq[McqQuestion])
  extends AnyTaskFormat with models.AutoCorrectable {

  override def correct(solution: AnyDeliveryFormat) = {
    solution match {
      case solution: McqSolution =>
        val score =
          questions.zip(solution.choices) count {
            case (question, choices) => question.check(choices)
          }
        Some((score.toFloat / questions.size) * 100)
      case _ => None
    }
  }
}
