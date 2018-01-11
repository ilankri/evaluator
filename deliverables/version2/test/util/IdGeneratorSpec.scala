package util

import org.scalatest._
import org.scalatest.Matchers._

class IdGeneratorSpec extends FlatSpec {
  val idGen: IdGenerator = new AnyRef with IdGenerator

  "An id generator" should
    "produce different values for two successive calls" in {
      idGen.nextId() should not be idGen.nextId()
    }
}
