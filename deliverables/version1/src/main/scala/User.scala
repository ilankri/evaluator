abstract class User(var id:Int, var name: String, var email:String, var pswd: String) {


  var tasks : List[String] = List("cours 2");
  var taskWorker : List[String] = List();

  def getTask() = {
    if (this.tasks.isEmpty) println("Votre liste des tâches est vide !");
    for (task <- this.tasks if !this.tasks.isEmpty)
      println(task);
  }






}

