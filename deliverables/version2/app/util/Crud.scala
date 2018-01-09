package util

trait Crud[Resource] {
  def create(resource: Resource): Unit

  def read(id: Long): Option[Resource]

  def update(resource: Resource): Unit

  def delete(id: Long): Unit
}
