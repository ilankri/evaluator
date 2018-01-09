package models

trait AutoCorrectable {
  def correct(solution: AnyDeliveryFormat): Option[Float]
}
