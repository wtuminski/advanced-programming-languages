package Exc_1
import akka.actor._

object Player {
    case object Odbij
    case class PlayWith(a1: ActorRef, a2: ActorRef)
}

class Player extends Actor {
    import Player._
    def receive: Receive = {
        case PlayWith(a1, a2) => 
            context.become(withPlayer(a1))
            a1 ! PlayWith(a2, self)
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
    val adam = system.actorOf(Props[Player](), name = "Adam")

    try {
        john ! PlayWith(kate, adam)
    } finally {
        system.terminate()
    }
    
}
