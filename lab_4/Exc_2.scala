// Zadanie 2. Korzystając z metod filter, map i zipWithIndex zdefiniuj funkcję:
// def remElems[A](seq: Seq[A], k: Int): Seq[A] = /* ... */
// która usunie k-ty element sekwencji seq.

package Exc_2
object Main extends App {
  def remElems[A](seq: Seq[A], k: Int): Seq[A] = {
    seq.zipWithIndex
      .filter(item => item._2 != k)
      .map(item => item._1);
  };
  println(remElems(List(0, 1, 2, 3, 4, 5, 6), 2));
};
