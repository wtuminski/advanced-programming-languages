// Zadanie 7. Korzystając z metod oferowanych przez kolekcje zdefiniuj funkcję:
// def diff[A](seq1: Seq[A], seq2: Seq[A]): Seq[A] = /* ... */
// która zwróci wszystkie elementy z seq1, które nie pasują wg "indeksów" z seq2.
// Przykład:
// Dla: seq1 = Seq(1, 2, 3), seq2 = Seq(2, 2, 1, 3) funkcja powinna zwrócić: Seq(1, 3), ponieważ
// seq1(0) != seq2(0) // zostawiamy
// seq1(1) != seq2(1) // usuwamy
// seq1(2) != seq2(2) // zostawiamy

package Exc_7

object Main extends App {
  def diff[A](seq1: Seq[A], seq2: Seq[A]): Seq[A] = {
    seq1.zipWithIndex
      .foldLeft(Seq[A]())((list, item) =>
        if (item._1 == seq2(item._2)) {
          list
        } else {
          item._1 +: list
        }
      )
      .reverse;
  };
  val seq1 = Seq(1, 2, 3);
  val seq2 = Seq(2, 2, 1, 3);
  println(diff(seq1, seq2));
};
