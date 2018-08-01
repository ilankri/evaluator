package models

/** Any delivery format must extend this class.  */
sealed abstract class AnyDeliveryFormat

/** A solution for a MCQ is just a matrix of choices.  */
case class McqSolution(choices: Seq[Seq[Boolean]]) extends AnyDeliveryFormat
