package controllers

import play.api.mvc._

class Tasks(cc: AppControllerComponents) extends TaskAbstractController(cc) {
  def form = TODO

  def create = TODO

  def read(id: Long) = TODO

  def readAll = workerAction { implicit request: WorkerRequest[AnyContent] =>
    Ok(views.html.tasks("All tasks", cc.db.readAllTasks))
  }

  def readAllDeliverables(id: Long) = TODO

  def register(taskId: Long, userId: Long) = TODO

  def unregister(taskId: Long, userId: Long) = TODO

  def deliver(id: Long) = TODO

  def delete(id: Long) = TODO
}
