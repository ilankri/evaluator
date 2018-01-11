package util

import org.scalatest._
import org.scalatest.Matchers._
import org.scalamock.scalatest.MockFactory

class IdGeneratorSpec extends FlatSpec with MockFactory {
  val idGen: IdGenerator = new AnyRef with IdGenerator

  "An id generator" should
    "produce different values for two successive calls" in {
      idGen.nextId() should not be idGen.nextId()
    }
}
