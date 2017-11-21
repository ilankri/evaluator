sealed abstract class Command
case object Quit extends Command
case class NewQuestion(label: String) extends Command
case class NewChoice(label: String, right: Boolean) extends Command
case object StartAnswer extends Command
case object Help extends Command

object InvalidCommand extends Throwable
object Exit extends Throwable
object StartQALoop extends Throwable

object Main {
  def eval(cmd: Command, mcq: MCQ): MCQ = cmd match {
    case Quit => throw Exit
    case StartAnswer => throw StartQALoop
    case NewQuestion(label) => mcq add Question(label, Seq())
    case NewChoice(label, right) => mcq add Choice(Proposition(label), right)
    case Help =>
      println(
        "help: Display this help\n" +
          "quit: Quit the program\n" +
          "newq <question>: Define a new question\n" +
          "newc <proposition> <status>: Define a new choice" +
          "with its status(true or false)\n" +
          "answer: Start answering the MCQ"
      )
      mcq
  }

  def parseCmd(command: String): Command = {
    val (cmd, rest) = command.span(_ != ' ')
    val args = rest.trim.split(' ')
    cmd match {
      case "quit" => Quit
      case "newq" if args.length >= 1 => NewQuestion(args(0))
      case "new" if args.length >= 2 =>
        NewChoice(args(0), args(1).toBoolean)
      case "answer" => StartAnswer
      case "help" => Help
      case _ => throw InvalidCommand
    }
  }

  def main(args: Array[String]): Unit = {
    var mcq = MCQ(Seq())
    var mcq_defined = false
    var answer = Answer(Seq())

    while (!mcq_defined) {
      print("> ")
      try {
        mcq = eval(parseCmd(io.StdIn.readLine()), mcq)
      } catch {
        case StartQALoop => mcq_defined = true
        case Exit => System.exit(0)
        case InvalidCommand => println("Invalid command.")
        case _: Throwable => println("Invalid command.")
      }
    }

    answer = Answer(Seq.fill(mcq.questions.length)(Set.empty))
    for (i <- 0 until mcq.questions.length) {
      println(mcq.questions(i).label)
      for (j <- 0 until mcq.questions(i).choices.length) {
        println(s"  ${j + 1}. ${mcq.questions(i).choices(j).proposition.label}")
      }
      print("> ")
      answer =
        try {
          Answer(
            answer.choices.updated(i, io.StdIn.readLine().split(' ').map(s =>
              mcq.questions(i).choices(s.toInt - 1).proposition).toSet)
          )
        } catch {
          case e: NumberFormatException => answer
        }
    }
    println("Result: " + (new AutoCorrector(mcq)).correct(answer) +
      "/" + mcq.questions.length)
  }
}
