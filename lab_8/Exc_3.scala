// Zadanie 3. Używając aktorów zaimplementuj "rozproszony licznik słów". Powinien on składać się z aktora głównego, typu Nadzorca
// class Nadzorca extends Actor { ... }
// oraz dynamicznie określanej (w momencie inicjowania działania nadzorcy) liczby aktorów "roboczych", typu Pracownik
// class Pracownik extends Actor { ... }
// Po uruchomieniu, Nadzorca powinien być w stanie przyjąć jedynie komunikat inicjalizacyjny postaci
// case class Init(liczbaPracownikow: Int)
// w którego efekcie powinien utworzyć zadaną liczbę aktorów typu Pracownik i przejść do stanu, w którym jest w stanie przyjmować "zlecenia" nadsyłane za pomocą komunikatów
// case class Zlecenie(tekst: List[String])
// Po otrzymaniu komunikatu Zlecenie nadzorca powinien zmienić swój stan oraz przekazywać poszczególne napisy z listy tekst do "obróbki" pracownikom. Służyć do tego powinny komunikaty
// case class Wykonaj( /* argumenty */ )
// Pracownicy zwracają informację do nadzorcy o liczbie znalezionych im napisie różnych słów (np. "Ulica" i "ulica" to identyczne słowa).
// case class Wynik( /* argumenty */ )
// Nadzorca sumuje/agreguje napływające wyniki oraz wysyła kolejny napis (jeżeli jeszcze jakiś istnieje) z listy tekst, pracownikowi od którego otrzymał wynik. Po otrzymaniu wszystkich odpowiedzi od pracowników i wyliczeniu wszystkich słów z listy, nadzorca wypisuje na konsoli wynik i wraca do stanu oczekiwania na kolejne zlecenie.

package Exc_3
import akka.actor._

object Boss {
    case class Init(numberOfWorkers: Int)
    case class Job(texts: List[String])
    case class Result(count: Int)
}

object Worker {
    case class Count(text: String, replyTo: ActorRef)
}

class Boss extends Actor {
    import Boss._
    import Worker._
    def receive: Receive = {
        case Init(numberOfWorkers) =>
            val workers = List.tabulate(numberOfWorkers)(i=>context.actorOf(Props[Worker](), name = s"Worker-$i"))
            context.become(waitingForJob(workers))
    }
    def waitingForJob(workers: List[ActorRef]): Receive = {
        case Init(_) =>
            println(s"\nI know my number of workers which is ${workers.size} and I am ready to take a first job.\n")
        case Job(texts) =>
            workers.zipWithIndex.foreach(i => if texts.length > i._2 then i._1 ! Count(texts(i._2), self))
            context.become(jobInProgress(workers, texts, texts.slice(workers.length, texts.length)))
    }
    def jobInProgress(
        workers: List[ActorRef], 
        originalTexts: List[String],
        remainingTexts: List[String], 
        numberOfWords: Int = 0, 
        numberOfResults: Int = 0
    ): Receive = {
        case Init(_) | Job(_) =>
            println("\nI'am busy at the moment, please come back later.\n")
        case Result(count) => 
            if(workers.contains(sender())) {
                remainingTexts match {
                    case Nil if numberOfResults + 1 == originalTexts.length => 
                        println(s"\nThe text included ${numberOfWords + count} different words\n")
                        context.become(waitingForJob(workers))
                    case _ => 
                        context.become(
                            jobInProgress(
                                workers, 
                                originalTexts,
                                if remainingTexts.isEmpty then Nil else remainingTexts.tail, 
                                numberOfWords + count, 
                                numberOfResults + 1
                            )
                        )
                        if !remainingTexts.isEmpty then sender() ! Count(remainingTexts.head, self)
                }   
            } else println("\nSorry, only my workers are allowed to send me their results.\n")
        

    }
}

class Worker extends Actor {
    import Worker._
    import Boss._
    def receive: Receive = {
        case Count(text, replyTo) =>
            val numberOfUniqueWords = text.toLowerCase().split(" ").map(_.trim).toList.zipWithIndex.toMap.size
            replyTo ! Result(numberOfUniqueWords)
    }
}

@main
def main() = {
    import Boss._
    import Worker._

    val system = ActorSystem("words_counter")
    val boss = system.actorOf(Props[Boss](), name = "boss") 
    
    try{
        boss ! Init(2)
        boss ! Job(List("It is a first text", "This is a second one and it is a bit longer", "The third one is here and it is the last one"))
    } finally {
        system.terminate()
    }
}