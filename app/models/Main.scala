object Main {
  def main(args: Array[String]): Unit = {

    val user1 = new Worker(1, "user1", "user1@test.fr","mdp123");
    //user.getTask();
    user1.register("nvx cours");
    user1.register("un deuxième nvx cours");


    val user2 = new Evaluator(2, "user2", "user2@test.fr","mdp121");

    val etudiant=new Worker(2,"Hamdi","h_h@gmail.com","passw")
    val subm=new Submission[String](1,etudiant,"21/11/2017","")
    println(subm.get_id())
    println(subm.get_author())
    println(subm.get_date())
    println(subm.get_content())

    //test class Task
    //test class Delivrable

  }

}
