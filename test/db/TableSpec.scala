package db

import org.scalatest._
import org.scalatest.Matchers._

class TableSpec extends FlatSpec {
  val tbl = Table.empty[util.Identifiable]
  val resource = new AnyRef with util.Identifiable { override val id = 1 }

  "An empty table" should
    "return an empty traversable when reading all the values" in {
      tbl.readAll shouldBe empty
    }

  "A table" should
    "contain a resource after this resource has been created" in {
      tbl create resource
      tbl read resource.id shouldBe 'defined
      tbl.readAll.find(_.id == resource.id) shouldBe 'defined
    }

  it should "not contain a resource after this resource has been deleted " in {
    tbl delete (resource.id)
    tbl read resource.id should not be 'defined
    tbl.readAll shouldBe empty
  }

}
