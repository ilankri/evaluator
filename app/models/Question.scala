case class Question(label: String, choices: Seq[Choice]) {
  def rightPropositions: Traversable[Proposition] =
    for (choice <- choices if choice.right) yield choice.proposition

  def add(choice: Choice) = Question(label, choices :+ choice)
}
