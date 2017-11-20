object Main extends App {
  override def main(args: Array[String]): Unit = {

    val user1 = new Worker(1, "user1", "user1@test.fr","mdp123");
    //user.getTask();
    user1.register("nvx cours");
    user1.register("un deuxième nvx cours");


    val user2 = new Evaluator(2, "user2", "user2@test.fr","mdp121");

  }

}
