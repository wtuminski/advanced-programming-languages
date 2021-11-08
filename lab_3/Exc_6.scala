// Zadanie 6. Zdefiniuj generyczną funkcję rekurencyjną
// def merge[A](a: List[A], b: List[A])(leq: (A, A) => Boolean): List[A]
// która połączy ze sobą dowolne dwa ciągi elementów typu A, zgodnie z porządkiem zadanym przez funkcję leq (załóżmy, że ciągi są posortowane). Zdefiniuj funkcję z użyciem rekurencji ogonowej.
// Przykład:
// Dla: a = List(1 ,3, 5, 8), b = List(2, 4, 6, 8, 10, 12) i leq = (m, n) => m < n), funkcja powinna zwrócić List(1, 2, 3, 4, 5, 6, 8, 8, 10, 12).

package Exc_6

import scala.annotation.tailrec

object Main extends App {
  def merge[A](a: List[A], b: List[A])(leq: (A, A) => Boolean): List[A] = {
    @tailrec
    def helper(
        a: List[A] = Nil,
        b: List[A] = Nil,
        acc: List[A] = Nil
    ): List[A] = {
      (a, b) match {
        case (Nil, Nil)      => acc
        case (Nil, bH :: bT) => helper(b = bT, acc = acc :+ bH)
        case (aH :: aT, Nil) => helper(a = aT, acc = acc :+ aH)
        case _ =>
          helper(a.tail, b.tail, acc ++ List(a.head, b.head).sortWith(leq));
      };
    };
    return helper(a, b);
  };

  val a = List(1, 3, 5, 8);
  val b = List(2, 4, 6, 8, 10, 12);

  println(merge(a, b)(leq = (m, n) => m < n));
};
