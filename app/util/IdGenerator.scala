package util

import java.util.concurrent.atomic.AtomicLong

/** A thread-safe generator of identifiers.  */
trait IdGenerator {
  private[this] val _nextId = new AtomicLong(1)

  /** Returns a fresh identifier.  */
  def nextId() = _nextId.getAndIncrement()
}
