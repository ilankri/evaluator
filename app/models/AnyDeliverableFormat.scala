package models

sealed abstract trait AnyDeliveryFormat

case class McqSolution(choices: Seq[Seq[Boolean]]) extends AnyDeliveryFormat
