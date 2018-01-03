package models

trait CanCorrect[Fmt] {
  def correct(solution: Fmt): (Int, Int)
}
