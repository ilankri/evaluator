package util

/** A trait for CRUD operations on some type of resource.  */
trait Crud[Resource] {
  /** Creates the given resource.  */
  def create(resource: Resource): Unit

  /**
    * Returns the resource with the given identifier if it exists.
    *
    * @return an option value containing the resource and `None` if there
    * is no such resource.
    */
  def read(id: Long): Option[Resource]

  def update(resource: Resource): Unit

  /** Deletes the resource with the given identifier.  */
  def delete(id: Long): Unit
}
