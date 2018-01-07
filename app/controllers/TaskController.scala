package controllers

class TaskController(cc: UserControllerComponents)
  extends UserAbstractController(cc) {
  def createPage = TODO

  def create = TODO

  def read(id: Long) = TODO

  def readAll = userAction { implicit request =>
    Ok(views.html.tasks("All tasks", cc.db.readAllTasks))
  }

  def readAllDeliverables(id: Long) = TODO

  def register(id: Long) = TODO

  def unregister(id: Long) = TODO

  def deliver(id: Long) = TODO

  def delete(id: Long) = TODO
}
