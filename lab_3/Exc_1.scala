// Zadanie 1. Zdefiniuj funkcję
// def sum(l: List[Option[Int]]): Option[Int] = /* ... */
// "sumującą”" listę opcjonalnych wartości całkowitych. Wynik powinien mieć wartość None wtedy i tylko wtedy, gdy wszystkie elementy listy l będą miały wartość None. W definicji skorzystaj z rekurencji ogonowej i "dopasowania wzorca".

package Exc_1

import scala.annotation.tailrec
object Main extends App {
  def sum(l: List[Option[Int]]): Option[Int] = {
    @tailrec
    def helper(
        l: List[Option[Int]],
        acc: Int = 0
    ): Option[Int] = {
      l match {
        case Nil                       => Some(acc)
        case None :: Nil if (acc == 0) => None
        case None :: lT                => helper(lT, acc)
        case Some(n) :: lT             => helper(lT, acc + n)
      };
    };
    return helper(l);
  };

  println(sum(List(Some(1), Some(2), Some(3), Some(4), Some(5))));
  println(sum(List(Some(1), Some(2), None)));
  println(sum(List(None, None)));
};
