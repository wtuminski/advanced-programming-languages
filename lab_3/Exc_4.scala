// Zadanie 4. Napisz generyczną funkcję
// def divide[A](list: List[A]): (List[A], List[A]) = /* ... */
// która podzieli listę list na dwie części. W pierwszej będą się znajdywać elementy na parzystych indeksach w drugiej elementy na nieparzystych. W definicji wykorzystaj rekurencję ogonową.
// Przykład:
// divide(List(1, 3, 5, 6, 7)) == (List(1, 5, 7), List(3, 6))

package Exc_4

import scala.annotation.tailrec

object Main extends App {
  def divide[A](list: List[A]): (List[A], List[A]) = {
    @tailrec
    def helper(
        list: List[A],
        evenList: List[A] = Nil,
        oddList: List[A] = Nil,
        index: Int = 0
    ): (List[A], List[A]) = {
      list match {
        case Nil => (evenList, oddList)
        case _ =>
          index % 2 match {
            case 0 =>
              helper(list.tail, evenList :+ list.head, oddList, index + 1)
            case _ =>
              helper(list.tail, evenList, oddList :+ list.head, index + 1)
          }
      };
    };
    return helper(list);
  };
  println(divide(List(1, 3, 5, 6, 7)));
};
