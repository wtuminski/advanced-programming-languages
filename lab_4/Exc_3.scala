// Zadanie 3. Korzystając z metody sliding zdefiniuj funkcję:
// def isOrdered[A](seq: Seq[A])(leq:(A, A) => Boolean): Boolean = /* ... */
// która zwróci informację czy wszystkie sąsiednie elementy w seq, są zgodne z predykatem leq.
// Przykłady:
// Dla seq = Seq(1, 2, 2, 4) i (_ < _) funkcja powinna zwrócić false.
// Dla seq = Seq(1, 2, 2, 4) i (_ <= _) funkcja powinna zwrócić true.

package Exc_3

object Main extends App {
  def isOrdered[A](seq: Seq[A])(leq: (A, A) => Boolean): Boolean = {
    seq
      .sliding(2)
      .map(item => (item(0), item(1)))
      .filterNot(item => leq(item._1, item._2))
      .toList
      .isEmpty
  };
  val seq = Seq(1, 2, 2, 4);
  println("should be false: " + isOrdered(seq)((_ < _)).toString());
  println("should be true: " + isOrdered(seq)((_ <= _)).toString());
};
