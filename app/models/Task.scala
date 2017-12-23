package models

import java.text.SimpleDateFormat

abstract class Task[format, worker](
    _Deadline: SimpleDateFormat,
    _workers: List[worker],
    _solution: format) {
  val Deadline = _Deadline: SimpleDateFormat
  var workers = _workers
  val solution = _solution
  def getsolution: format = { return solution }
  def addWorker(w: worker) = { workers = w :: workers }
  def deleteWorker(w: worker) = {
    val newList = workers.filter(_ != w)
  }

}
