package util

import java.util.concurrent.atomic.AtomicLong

trait IdGenerator {
  private[this] val _nextId = new AtomicLong(1)

  def nextId() = _nextId.getAndIncrement()
}
