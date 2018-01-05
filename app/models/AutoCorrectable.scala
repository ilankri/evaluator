package models

trait AutoCorrectable {
  def correct(solution: Any): Option[(Int, Int)]
}
