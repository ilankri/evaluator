package db

trait Db[Resource <: util.Identifiable] extends util.Crud[Resource] {
  import collection.concurrent._

  private[this] val db: Map[Long, Resource] = TrieMap.empty

  override def create(resource: Resource) = db += (resource.id -> resource)

  override def read(id: Long) = db get id

  override def update(resource: Resource) = ???

  override def delete(id: Long) = db -= id

  def readAll: Traversable[Resource] = db.values
}

object Db {
  object Users extends Db[models.User]

  object Submissions extends Db[models.Submission[Any]]
}
