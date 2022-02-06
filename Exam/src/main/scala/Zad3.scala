import akka.actor._

object Zad3 {

  case class Przyjmij(lUcz: List[ActorRef])
  case object Wygeneruj
  case class Poprawne(l: List[Int])
  case object Pytanie
  case class Odpowiedzi(l: List[Int])

  class Nadzorca extends Actor {

    def check(correct: List[Int], list: List[Int]): Int = {
      list.zipWithIndex.map(a => {
        val point = if a._1 == correct(a._2) then 1 else 0
        point
      })
      .reduce((a,b)=>a+b)
    }


    def receive: Receive = { 
      case Przyjmij(lUcz) =>
        if lUcz.length >= 20 then {
          context.become(ready(lUcz))
          val pytanie = context.actorOf(Props[Pytanie]())
          pytanie ! Wygeneruj
        } else context.become(withSomePlayers(lUcz))
      case _ => println("Unknown command")
    }

    def withSomePlayers(lUcz: List[ActorRef]): Receive = {
      case Przyjmij(newLUcz) =>
        val fullLUcz = lUcz ++ newLUcz
        if fullLUcz.length >= 20 then {
          context.become(ready(fullLUcz))
          val pytanie = context.actorOf(Props[Pytanie]())
          pytanie ! Wygeneruj
        } else context.become(withSomePlayers(fullLUcz))
      case _ => println("Unknown command")
    }

    def ready(lUcz: List[ActorRef]): Receive = {
      case Poprawne(l: List[Int]) =>
        context.become(waiting(lUcz.length, l))
        lUcz.foreach(u=> u ! Pytanie)
      case _ => println("Unknown command")
    }

    def waiting(numberOfStudents: Int, correctL: List[Int], answers: List[(ActorRef, List[Int])] = List()): Receive = {
      case Odpowiedzi(l: List[Int]) => 
        if answers.length + 1 < numberOfStudents
        then {
          context.become(waiting(numberOfStudents, correctL, answers :+ (sender(), l)))
        } else {
          val fullAnswers = answers :+ (sender(), l)
          val checked = fullAnswers.map(a => (a._1, check(correctL, a._2)))
          
          val sorted = checked.sortBy(a => a._2 * -1)
          val best = sorted.filter(a => a._2 == sorted.head._2)
          best.foreach(a => println(s"${a._1.path.name} ${a._2}"))

          if best.length == 1 then {
            println(s"Zwycięzca: ${best.head._1.path.name} - ${best.head._2}")
          } else {
            context.become(ready(best.map(a => a._1)))
            val pytanie = context.actorOf(Props[Pytanie]())
            pytanie ! Wygeneruj
          }
        }

    }
  }

  class Uczestnik extends Actor {
    def receive: Receive = {
      case Pytanie =>
        val r = util.Random
        val values = List.tabulate(10)(_ => r.between(1,5)) 
        sender() ! Odpowiedzi(values)
    }
  }

  class Pytanie extends Actor {
    def receive: Receive = {
      case Wygeneruj => 
        val r = util.Random
        val values = List.tabulate(10)(_ => r.between(1,5)) 
        sender() ! Poprawne(values)
    }
  }

  def main(args: Array[String]): Unit = {
    val r = util.Random
    // r jest „generatorem”, którego należy użyć do generowania wartości
    // losowych różnych typów (i zakresów) np. r.nextInt, r.nextInt(100)

    val system = ActorSystem("teleturniej")
    val nadzorca = system.actorOf(Props[Nadzorca]())

    val uczestnicy = List.tabulate(20)(_ => system.actorOf(Props[Uczestnik]()))
    nadzorca ! Przyjmij(uczestnicy)
  }

}
