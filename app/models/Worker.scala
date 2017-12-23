package models

class Worker(id: Int, name: String, email: String, pswd: String) extends User(id, name, email, pswd) {

  def register(task: String): Unit = {
    taskWorker = task :: taskWorker;
    tasks = tasks ::: taskWorker;
    println("Tu t'es inscrit au cours de " + task + ".\n Voici la nouvelle liste des tâches: ");
    var taskList = tasks.foreach { println }
  }

  def unregister(task: String): Unit = {

  }

}
