package util

import java.util.concurrent.atomic.AtomicReference

private[util] class SynchronizedSet[A](_elems: Set[A])
  extends collection.generic.Growable[A]
  with collection.generic.Shrinkable[A] {
  private[this] val elems = new AtomicReference(_elems)

  def +=(elem: A) = {
    elems.updateAndGet(_ + elem)
    this
  }

  def -=(elem: A) = {
    elems.updateAndGet(_ - elem)
    this
  }

  def toSet = elems.get()

  def clear() = elems.set(Set.empty)
}

object SynchronizedSet {
  def apply[A](elems: A*) = new SynchronizedSet(elems.toSet)

  def empty[A] = apply[A]()
}
