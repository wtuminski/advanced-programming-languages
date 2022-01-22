// Zadanie 1. Stwórz symulację bitwy dwóch rodów używając aktorów. Każdy ród posiada Zamek i 500 Łuczników. Żeby walka była uczciwa pojedynczy Planista (Scheduler) wydaje obu Zamkom rozkaz strzelania, który rozsyłany jest wśród Łuczników, którzy w danej chwili bronią murów. Zasady walki stanowią:

//     Zamki zaczynają ze 100 Łucznikami, których nazwiemy "aktywnych obrońców";
//     W każdej chwili Zamku może bronić maksymalnie 100 aktywnych obrońców;
//     Scheduler wysyła obu Zamkom rozkaz strzelania co 1 sekundę;
//     Strzały trafiają w Zamek i spadają na jego Łuczników, mając szansę na trafienie równą ([liczba_aktywnych_obrońców]/(2 * 100)), np:
//         gdy przeciwnik ma 100 aktywnych Łuczników, nasz strzał ma 50% szansy na trafienie;
//         gdy przeciwnik ma 50 aktywnych Łuczników, nasz strzał ma 25% szansy na trafienie.
//     Obrońca traci życie gdy otrzyma postrzał;
//     Zamek uzupełnia swoich obrońców do momentu wyczerpania wszystkich rezerwowych Łuczników;
//     Żeby być zdolnym do walki Zamek musi mieć przynajmniej jednego aktywnego Łucznika;
//     Gdy wszyscy Łucznicy z danego Zamku zginą, ogłasza on, że przegrał bitwę – kończy to symulację (system.terminate).

// Uwagi do rozważenia:

//     To, że strzelanie odbywa się co 1 sekundę nie jest ważne – może to być 0.01 sekundy żeby bitwa trwała krócej. Ważne żeby każdy Łucznik miał taką samą szybkość strzału.
//     Możliwe są różne strategie uzupełniania poległych obrońców. Pytanie, czy lepiej częściej uzupełniać Łuczników, aby mieć ich jak najwięcej czy trzymać się mniejszej liczby – aby strzały przeciwnika częściej pudłowały?

// Uwagi techniczne:

//     Zaimplementuj symulację i wymyśl/przetestuj przynajmniej dwie różne strategie uzupełniania Łuczników. Przeprowadzając kilka symulacji, sprawdź która najlepiej się sprawdza.
//     W celu poinformowania Zamku, że jego obrońca ginie, z poziomu Zamku, wykorzystaj metodę context.watch(obrońca). Wówczas w przypadku śmierci obrońcy Zamek automatycznie otrzyma komunikat Terminated(obrońca), na który powinien sensownie zareagować.

// Uwagi "metodologiczne":

//     Na czym polegają Twoje strategie i która z nich działa najlepiej?
//     Możesz skonfrontować swoje strategie ze strategiami opracowanymi przez innych studentów i sprawdzić jaka strategia daje najlepsze wyniki.

package Exc_1
import akka.actor._
import scala.annotation.tailrec
import java.util.UUID
import scala.util.Random
import scala.language.postfixOps
import scala.concurrent.duration._

val maxArchers = 500

enum StrategyType {
    case fullCapacity
    case halfCapacity
}

object Castle {
    case class Init(strategy: StrategyType, target: ActorRef)
    case object Attack
    case object AnnounceVictory
    case object ReceiveShot
}

object Archer {
    case class Fire(target: ActorRef)
}


class Castle extends Actor {
    import Castle._
    import Archer._
    val randomGenerator = Random()
    def createArcher(): ActorRef = {
        context.watch(
            context.system.actorOf(
                Props[Archer](), 
                name=s"${self.path.name}-archer-${UUID.randomUUID()}"
            )
        )
    }

    def receive: Receive = {
        case Init(strategy, target) =>
            val archers = List
                .tabulate(100)(i => this.createArcher())
            context.become(withArchers(archers, strategy, target))
    }

    def withArchers(
        archers: List[ActorRef],
        strategy: StrategyType,
        target: ActorRef,
        numberOfUsedArchers: Int = 0
    ): Receive = {
        case Attack =>
            @tailrec
            def fire(archers: List[ActorRef]): Unit = {
                if !archers.isEmpty then {
                    archers(0) ! Fire(target)
                    fire(archers.tail)
                }
            }
            fire(archers)

        case ReceiveShot =>
            val probability = archers.length.toFloat / (2 * 100)
            val x = this.randomGenerator.nextFloat()
            val wasShotAccurate = if x <= probability 
                then true else false
            if wasShotAccurate then archers(this.randomGenerator.between(0, archers.length)) ! PoisonPill

        case AnnounceVictory =>
            println(s"\n${self.path.name}: I have won, my strategy was $strategy and my ${numberOfUsedArchers + 1} archers have died\n")

        case Terminated(archer: ActorRef) =>
            if (numberOfUsedArchers == (maxArchers - 1)) {
                println(s"\n${self.path.name}: I have lost, my strategy was $strategy and my ${numberOfUsedArchers + 1} archers have died\n")
                target ! AnnounceVictory
                context.system.terminate()
            } else {
                val canCreateMoreArchers: Boolean = (archers.length + numberOfUsedArchers) < maxArchers
                val newArchers: List[ActorRef] = strategy match {
                    case StrategyType.fullCapacity if archers.length < 100 && canCreateMoreArchers  => 
                        archers.filter(_ != archer) :+ this.createArcher()
                    case StrategyType.halfCapacity if archers.length < 50 && canCreateMoreArchers =>
                        archers.filter(_ != archer) :+ this.createArcher()
                    case _ => 
                        archers.filter(_ != archer)
                }
                context.become(
                    withArchers(
                        newArchers,
                        strategy,
                        target,
                        numberOfUsedArchers + 1
                    )
                )
            }
    }
}

class Archer extends Actor {
    import Archer._
    import Castle._
    def receive: Receive = {
        case Fire(target) =>
            target ! ReceiveShot
    }
}

@main
def main() = {
    import Castle._
    val system = ActorSystem("battle")
    val firstCastle = system.actorOf(Props[Castle](), name="FirstCastle")
    val secondCastle = system.actorOf(Props[Castle](), name="SecondCastle")

    firstCastle ! Init(StrategyType.halfCapacity, secondCastle)
    secondCastle ! Init(StrategyType.fullCapacity, firstCastle)

    import system.dispatcher
    system.scheduler.scheduleAtFixedRate(50 milliseconds, 500 milliseconds){()=>
        firstCastle ! Attack
        secondCastle ! Attack
    }
}