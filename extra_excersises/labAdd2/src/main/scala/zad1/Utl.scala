package zad1

import org.scalacheck._
import uk.gov.hmrc.smartstub._
import scala.annotation.tailrec
import akka.actor.{ActorRef}

// Generator osób i ocen
object Utl {
  import scala.util.Random
  val rand = new Random
  val próbaUdana = 0.05

  def osoba(): Osoba = {
    val imie = Gen.forename().sample.get.split(" ").reduce((a, b) => a + b)
    val nazwisko = Gen.surname.sample.get.split(" ").reduce((a, b) => a + b)
    Osoba(imie, nazwisko)
  }

  def ocena(): Option[Ocena] = {
    if (rand.nextDouble() > próbaUdana) {
      val nota1 = rand.nextInt(21)
      val nota2 = rand.nextInt(21)
      val nota3 = rand.nextInt(21)
      Some(Ocena(nota1, nota2, nota3))
    } else {
      None
    }
  }

  /*
  o1 > o2 jeśli:
    - suma1 > suma2  (suma = nota1 + nota3 + nota3)
    - suma1 == suma2 oraz o1.nota1 > o2.nota1
    - suma1 == suma2, o1.nota1 == o2.nota1 oraz o1.nota3 > o2.nota3

  Jeśli o1 i o2 mają identyczne wszystkie noty to mamy REMIS

   */

  private def getNextPlace(
      notes: Ocena,
      prevNotes: Ocena,
      prevPlace: Int,
      prevPrevPlace: Int
  ): Int = {
    val sum = notes.nota1 + notes.nota2 + notes.nota3
    val prevSum = prevNotes.nota1 + prevNotes.nota2 + prevNotes.nota3
    if (
      prevSum > sum
      || (prevSum == sum && prevNotes.nota1 > notes.nota1)
      || (prevSum == sum && prevNotes.nota1 == notes.nota1 && prevNotes.nota2 > notes.nota2)
    ) return if (prevPlace == prevPrevPlace) prevPlace + 2 else prevPlace + 1
    else return prevPlace
  }

  def countResults(
      scores: List[(ActorRef, Option[Ocena])]
  ): List[(Int, ActorRef, Ocena)] = {

    val sortedScores = scores
      .map(score =>
        score._2 match {
          case Some(s) => (score._1, s)
          // case _       => 0
        }
      )
      .sortBy(s =>
        (
          (s._2.nota1 + s._2.nota2 + s._2.nota3) * -1,
          s._2.nota1 * -1,
          s._2.nota2 * -1
        )
      )

    @tailrec
    def helper(
        scores: List[(ActorRef, Ocena)],
        acc: List[(Int, ActorRef, Ocena)] = List()
    ): List[(Int, ActorRef, Ocena)] = {
      scores match {
        case Nil => acc
        case h :: t =>
          val prevPrevPlace = if (acc.length >= 2) acc(acc.length - 2)._1 else 0
          val place =
            if (acc.isEmpty) 1
            else getNextPlace(h._2, acc.last._3, acc.last._1, prevPrevPlace)
          helper(t, acc :+ (place, h._1, h._2))
      }
    }
    helper(sortedScores)
  }

}
