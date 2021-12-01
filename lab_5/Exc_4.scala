// Zadanie 4. Gra MaterMind polega na odgadnięciu w jakich miejscach zostały umieszczone n ukrytych kul, które są oznaczone powtarzającymi się kolorami. Gracz wygrywa, jeżeli w określonej liczbie ruchów odgadnie w jakich miejscach zostały umieszczone kule. W każdej turze gracz wybiera n kul, po czym zostaje mu wyświetlona informacja czy trafił. Każda prawidłowo odgadnięta kula (kula o właściwym kolorze na właściwym miejscu) sygnalizowana jest czarną kropką. Natomiast jeżeli gracz odgadł kolor kuli, ale nie odgadł jej lokalizacji, jest to sygnalizowane białą kropką. Gracz nie wie, które kule są właściwe, które zaś nie.
// Korzystając z metod oferowanych przez kolekcję zdefiniuj metodę oceniania ruchów dla gry MaterMind, czyli zwraca liczbę białych i czarnych kropek.
// Przykład:
// def score(code: Seq[Int])(move: Seq[Int]): (Int, Int)
// Przykład:
// val code = Seq(1, 3, 2, 2, 4, 5)
// val move = Seq(2, 1, 2, 4, 7, 2)
// Funkcja powinna zwrócić: (1, 3)

package Exc_4

// [Int, Int] - [czarne, białe]
object Main extends App {
  def score(code: Seq[Int])(move: Seq[Int]): (Int, Int) = {
    val codeItems = code.groupBy(identity).mapValues(_.size);
    val moveItems = move.groupBy(identity).mapValues(_.size);
    val allRepeats = move
      .filter(value => code.contains(value))
      .toSet
      .reduce((acc, item) =>
        if (codeItems(item) >= moveItems(item)) acc + moveItems(item)
        else acc + codeItems(item)
      )
    val blacks = code.zipWithIndex
      .foldLeft(0)((acc: Int, item) =>
        if (item._1 == move(item._2)) acc + 1 else acc + 0
      );
    return (blacks, allRepeats - blacks);
  }
  val code = Seq(1, 3, 2, 2, 4, 5)
  val move = Seq(2, 1, 2, 4, 7, 2)
  println(score(code)(move))
}
