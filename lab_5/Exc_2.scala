// Zadanie 2. Korzystając z metod oferowanych przez kolekcje zdefiniuj funkcję:
// def swap[A](seq: Seq[A]): Seq[A] = /* ... */
// która zamieni kolejnością wartości znajdujących się na parzystych i nieparzystych indeksach.
// Przykład:
// Dla: seq = Seq(1, 2, 3, 4, 5), Seq(2, 1, 4, 3, 5).
package Exc_2
object Main extends App {
  def swap[A](seq: Seq[A]): Seq[A] = {
    seq.zipWithIndex
      .foldLeft(List[A]())((list, current) =>
        current._2 % 2 match {
          case 0 => current._1 +: list
          case _ => list.head +: current._1 +: list.tail
        }
      )
      .reverse
  }
  val seq = Seq(1, 2, 3, 4, 5)
  println(swap(seq))
}
