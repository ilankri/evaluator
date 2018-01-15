package models

/** A task format is auto-correctable if it implements this trait.  */
trait AutoCorrectable {
  /**
    * Auto-corrects the given solution if its format matches the
    * expected delivery format.
    *
    * @return an option value containing the evaluation and `None` if
    * `solution` is not auto-correctable, i.e. the format of `solution`
    * is wrong.
    */
  def correct(solution: AnyDeliveryFormat): Option[Float]
}
