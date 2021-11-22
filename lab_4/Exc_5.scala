// Zadanie 5. Korzystając z metody foldLeft/foldRight i zdefiniuj generyczną funkcję:
// def deStutter[A](seq: Seq[A]): Seq[A] = /* ... */
// która usunie z sekwencji seq wszystkie powtarzające się ciągi.
// Przykład:
// Dla: seq = Seq(1, 1, 2, 4, 4, 4, 1, 3), funkcja powinna zwrócić: Seq(1, 2, 4, 1, 3).

package Exc_5

object Main extends App {
  def deStutter[A](seq: Seq[A]): Seq[A] = {
    return seq
      .foldLeft(List[A]())((list, item) => {
        list match {
          case Nil                      => item +: list;
          case lH :: lT if (item != lH) => item +: list;
          case _                        => list;
        }
      })
      .reverse;
  };
  val seq = Seq(1, 1, 2, 4, 4, 4, 1, 3);
  println(deStutter(seq));
};
