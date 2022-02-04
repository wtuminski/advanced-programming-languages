package zad1

import akka.actor.{Actor, ActorRef, Props}

object Organizator {
  case object Start
  // rozpoczynamy zawody – losujemy 50 osób, tworzymy z nich zawodników
  // i grupę eliminacyjną
  case object Runda
  // polecenie rozegrania rundy (kwalifikacyjnej bądź finałowej) –  wysyłamy Grupa.Runda
  // do aktualnej grupy
  case object Wyniki
  // polecenie wyświetlenia klasyfikacji dla aktualnej grupy
  case class Wyniki(w: Map[ActorRef, Option[Ocena]])
  // wyniki zwracane przez Grupę
  case object Stop
  // kończymy działanie
}

class Organizator extends Actor {
  // importujemy komunikaty na które ma reagować Organizator
  import Organizator._

  def receive: Receive = {
    case Start =>
      val people: List[Osoba] =
        List.tabulate(50)(i => Utl.osoba())
      val players: List[ActorRef] =
        people.map(p =>
          context.actorOf(Props[Zawodnik](), s"${p.imie}_${p.nazwisko}")
        )
      val eliminationGroup =
        context.actorOf(Props(new Grupa(players)), "eliminations")
      context.become(eliminations(eliminationGroup))
    case Stop =>
      println("kończymy zawody...")
      context.system.terminate()
  }

  def eliminations(group: ActorRef): Receive = {
    case Runda =>
      group ! Grupa.Runda
    case Wyniki =>
      group ! Grupa.Wyniki
    case Wyniki(wyniki) =>
      println()
      wyniki.toList.foreach(w =>
        println(
          s"${w._1.path.name.split("_").mkString(" ")} - ${w._2 match {
            case Some(s) => s"${s.nota1}-${s.nota2}-${s.nota3}"
            case _       => ""
          }}"
        )
      )
      context.become(beforeFinals(wyniki, group))
    case Stop =>
      println("kończymy zawody...")
      context.system.terminate()
  }

  def beforeFinals(
      eliminationScores: Map[ActorRef, Option[Ocena]],
      group: ActorRef
  ): Receive = { case Runda =>
    group ! Grupa.Koniec
    val playersWithScores: List[(ActorRef, Option[Ocena])] =
      eliminationScores.toList
    val bestPlayersWithScores = playersWithScores
      .sortBy(player => {
        player._2 match {
          case Some(s) => (s.nota1 + s.nota2 + s.nota3) * -1
          case _       => 0
        }
      })
      .slice(0, 20)
    val bestPlayers = bestPlayersWithScores.map(player => player._1)
    val finalGroup = context.actorOf(Props(new Grupa(bestPlayers)), "final")
    context.become(finals(eliminationScores, finalGroup))
    finalGroup ! Grupa.Runda
  }

  def finals(
      eliminationScores: Map[ActorRef, Option[Ocena]],
      finalGroup: ActorRef
  ): Receive = {
    case Wyniki =>
      finalGroup ! Grupa.Wyniki
    case Wyniki(wyniki) =>
      val finalScores = Utl.countResults(wyniki.toList)
      println()
      finalScores.foreach(w =>
        println(
          s"${w._1}. ${w._2.path.name
            .split("_")
            .mkString(" ")} - ${w._3.nota1}-${w._3.nota2}-${w._3.nota3}=${w._3.nota1 + w._3.nota2 + w._3.nota3} "
        )
      )
    // TODO - zsumuj wyniki
    case Stop =>
      println("kończymy zawody...")
      context.system.terminate()

  }
}
