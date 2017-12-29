package util

import java.util.concurrent.atomic.AtomicReference

private[util] class SynchronizedSet[A](_set: Set[A]) {
  private[this] val set = new AtomicReference(_set)

  def +=(elem: A) = {
    set.updateAndGet(_ + elem)
    this
  }

  def -=(elem: A) = {
    set.updateAndGet(_ - elem)
    this
  }

  def toSet = set.get()
}

object SynchronizedSet {
  def apply[A](elems: A*) = new SynchronizedSet(elems.toSet)

  def empty[A] = apply[A]()
}
