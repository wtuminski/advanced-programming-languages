// Zadanie 5. Korzystając z metod oferowanych przez kolekcje, skonstruuj "histogram" ilustrujący częstotliwość występowanie w tekście poszczególnych liter w pliku ogniem_i_mieczem.txt (autor H. Sienkiewicz). Małe i wielkie litery traktuj jako identyczne. Pomiń występujące w tekście znaki interpunkcyjne (kropki, przecinki, myślniki, cudzysłowy itp).
// Rozwiązanie przedstaw w postaci funkcji
// def histogram(max: Int): Unit
// która przyjmuje argument max oznacza maksymalną szerokość histogramu (jeżeli liter jest więcej histogram nie powinien przekroczyć max).
// Przykład:
// a:***************************************
// ą:**********
// b:*****************
// c:**************
// ć:*******
// ...
// Podpowiedź: mogą się przydać metody standardowe, takie jak np. isLetter, toLowerCase, toUpperCase itp.

package Exc_5

object Main extends App {
  def histogram(max: Int): Unit = {
    val ogniemIMieczem =
      scala.io.Source.fromFile("./ogniem_i_mieczem.txt").mkString
    val usedLetters = ogniemIMieczem.toLowerCase
      .filter(char => char.isLetter)
      .groupBy(identity)
      .mapValues(_.size)
      .toList
      .sortWith((a, b) => a._1 < b._1)

    usedLetters.foreach(letter =>
      println(s"${letter._1}: ${"*" * List(letter._2, max).min}")
    )
  }

  histogram(100)
}
