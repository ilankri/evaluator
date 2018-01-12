package models

object Mock extends util.IdGenerator {
  def name = "name"

  def email = s"$name@mock.com"

  def password = "password"

  def user = User(name, email, password, User.Student)

  def student: Student = new Student(nextId(), name, email, password)

  def instructor: Instructor = new Instructor(nextId(), name, email, password)

  def evaluator: Evaluator =
    new User(nextId(), name, email, password) with Evaluator

  def worker: Worker = new User(nextId(), name, email, password) with Worker

  def taskFormat: AnyTaskFormat = Mcq(Seq.empty)

  def deliveryFormat: AnyDeliveryFormat = McqSolution(Seq.empty)

  def description = "Description"

  def task = new Task(user, description, taskFormat)

  def deliverable = new Deliverable(user, description, deliveryFormat, task)

  def evaluation: Float = 0
}
