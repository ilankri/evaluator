package util

import java.util.concurrent.atomic.AtomicReference

/** A mutable structure of set for safe concurrent accesses.  */
private[util] class SynchronizedSet[A](_elems: Set[A])
  extends collection.generic.Growable[A]
  with collection.generic.Shrinkable[A] {
  private[this] val elems = new AtomicReference(_elems)

  /**
    *  Adds an element to the set.
    *
    * @return the set itself.
    */
  def +=(elem: A) = {
    elems.updateAndGet(_ + elem)
    this
  }

  /** Removes an element from the set.
    *
    * @return the set itself.
    */
  def -=(elem: A) = {
    elems.updateAndGet(_ - elem)
    this
  }

  /** Returns true if and only if the given element is in the set.  */
  def contains(elem: A) = elems.get() contains elem

  /** Convert this concurrent set to a standard immutable set.  */
  def toSet = elems.get()

  def clear() = elems.set(Set.empty)
}

object SynchronizedSet {
  def apply[A](elems: A*) = new SynchronizedSet(elems.toSet)

  def empty[A] = apply[A]()
}
