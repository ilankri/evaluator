package db

import util._

/**
  * A table of a database is a pool of identifiable resources on which
  * we can make CRUD operations.
  */
private[db] class Table[Resource <: Identifiable](
    resources: Traversable[Resource])
  extends Crud[Resource] {
  import collection.concurrent._

  private[this] val tbl: Map[Long, Resource] =
    TrieMap(
      (for (resource <- resources) yield (resource.id -> resource)).toSeq: _*
    )

  override def create(resource: Resource) = tbl += (resource.id -> resource)

  override def read(id: Long) = tbl get id

  override def update(resource: Resource) = tbl update (resource.id, resource)

  override def delete(id: Long) = tbl -= id

  def readAll: Traversable[Resource] = tbl.values
}

/** Factory for creating database table.  */
private[db] object Table {
  def apply[A <: Identifiable](resources: A*) = new Table[A](resources)

  def empty[A <: Identifiable] = apply[A]()
}

/**
  * A "credential table" is like a standard table, except that, besides
  * looking up an element by its ID, we can also lookup for element by
  * credentials(name and password).
  */
private[db] class CredentialTable[Resource <: CredentialTable.WithCredentials](
    resources: Traversable[Resource])
  extends Table[Resource](resources) {
  def read(name: String, password: String) =
    readAll.find(resource =>
      resource.name == name && resource.checkPassword(password)
    )
}

private[db] object CredentialTable {
  private type WithCredentials = Identifiable with Authenticatable

  def apply[A <: WithCredentials](resources: A*) =
    new CredentialTable[A](resources)

  def empty[A <: WithCredentials] = apply[A]()
}
