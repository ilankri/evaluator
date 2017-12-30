package db

class Table[Resource <: util.Identifiable] extends util.Crud[Resource] {
  import collection.concurrent._

  private[this] val tbl: Map[Long, Resource] = TrieMap.empty

  override def create(resource: Resource) = tbl += (resource.id -> resource)

  override def read(id: Long) = tbl get id

  override def update(resource: Resource) = ???

  override def delete(id: Long) = tbl -= id

  def readAll: Traversable[Resource] = tbl.values
}

object Table {
  def apply[A <: util.Identifiable](resources: A*) = {
    val tbl = new Table[A]

    resources foreach (tbl create _)
    tbl
  }

  def empty[A <: util.Identifiable] = apply[A]()
}
