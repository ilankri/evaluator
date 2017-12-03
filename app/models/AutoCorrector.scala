class AutoCorrector(mcq: MCQ) {
  def correct(answer: Answer) =
    mcq.questions.zip(answer.choices) count {
      case (question, choices) => question.rightPropositions.toSet == choices
    }
}
