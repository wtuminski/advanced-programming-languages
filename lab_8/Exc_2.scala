// Zadanie 2. Zmodyfikuj/uogólnij rozwiązanie z zadania 1 tak, aby rozgrywka mogła odbywać się "po okręgu" składającym się z listy, zawierającą dowolną liczbę, aktorów.

package Exc_2
import akka.actor._

object Player {
    case object Odbij
    case class PlayWith(firstPlayer: ActorRef, numberOfExtraPlayers: Int)
}

class Player extends Actor {
    import Player._
    def receive: Receive = {
        case PlayWith(firstPlayer, numberOfExtraPlayers) => 
            val nextPlayer = if numberOfExtraPlayers > 0
                then context.actorOf(Props[Player](), name = s"actor-$numberOfExtraPlayers")
                else firstPlayer
            context.become(withPlayer(nextPlayer))
            nextPlayer ! PlayWith(firstPlayer, numberOfExtraPlayers - 1)
    }
    def withPlayer(a1: ActorRef): Receive = {
        case PlayWith(_, _) =>
            println(s"${self.path.name}: zaczynam grać z ${a1.path.name}")
            a1 ! Odbij    
        case Odbij =>
            println(s"${self.path.name}: odbijam do ${a1.path.name}")
            a1 ! Odbij
    }
}

@main
def main() = {
    import Player._
    val system = ActorSystem("pingpong")

    val john = system.actorOf(Props[Player](), name = "John")
    val kate = system.actorOf(Props[Player](), name = "Kate")

    try {
        john ! PlayWith(john, 4)
    } finally {
        system.terminate()
    }
    
}
