// Zadanie 4. Korzystając z metody groupBy zdefiniuj funkcję:
// def freq[A](seq: Seq[A]): Seq[(A, Int)] = /* ... */
// która zwróci częstość wystąpienia poszczególnych elementów w ciągu seq.
// Przykład:
// Dla seq = Seq('a','b','a','c','c','a') funkcja powinna zwrócić Seq(('a', 3),('b', 1),('c', 2)).
package Exc_4

object Main extends App {
  def freq[A](seq: Seq[A]): Seq[(A, Int)] = {
    seq.groupBy(x => x).map(i => (i._1, i._2.length)).toSeq;
  };
  val seq = Seq('a', 'b', 'a', 'c', 'c', 'a');
  println(
    "Should return Seq(('a', 3),('b', 1),('c', 2)): " + freq(seq).toString
  );
};
