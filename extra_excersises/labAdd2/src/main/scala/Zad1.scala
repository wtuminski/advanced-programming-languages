// „Interfejs użytkownika” wymaga pewnych dodatkowych elementów:
import scala.concurrent.ExecutionContext
import scala.util.control.Breaks._
import scala.io.StdIn

import akka.actor.{ActorSystem, Props}
import zad1._

object Zad1 extends App {

  val system = ActorSystem("system")
  val organizator = system.actorOf(Props[Organizator](), "organizator")

  // Interfejs „organizatora”:
  implicit val ec: ExecutionContext = ExecutionContext.global

  breakable {
    while (true) {
      StdIn.readLine("polecenie: ") match {
        case "start" =>
          // początek zawodów
          organizator ! Organizator.Start
        case "eliminacje" =>
          // polecenie rozegrania rundy eliminacyjnej
          organizator ! Organizator.Runda
        case "finał" =>
          // polecenie rozegrania rundy finałowej
          // wymaga zamknięcia Rundy eliminacyjnej i utworzenie
          // Rundy finałowej, zawierającej najlepszych 20.
          // zawodników z Rundy eliminacyjnej
          organizator ! Organizator.Runda
        case "wyniki" =>
          organizator ! Organizator.Wyniki
        // żądanie rankingów (lub rankingu finałowego)
        case "koniec" =>
          organizator ! Organizator.Stop
          break()
        case _ =>
          println("unknown command")
      }
    }
  }

}
