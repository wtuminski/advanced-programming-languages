// Zadanie 5. Napisz generyczną funkcję
// def compress[A](list: List[A]): List[(A, Int)]
// która w liście list zastępuje każdy podciąg powtarzających się elementów a...a parą (a, długość podciągu).
// Przykład:
// compress(List('a','a','b','c','c','c','d','d','c')) == List( ('a',2), ('b',1), ('c',3), ('d',2), ('c',1) )
// Skorzystaj z rekurencji ogonowej i "dopasowania wzorca".

package Exc_5

import scala.annotation.tailrec

object Main extends App {
  def compress[A](list: List[A]): List[(A, Int)] = {
    @tailrec
    def helper(
        list: List[A],
        finalList: List[(A, Int)] = Nil,
        prevElem: A,
        count: Int
    ): List[(A, Int)] = {
      list match {
        case Nil if (finalList.last != prevElem) => finalList :+ (prevElem, 1)
        case Nil => finalList :+ (prevElem, count + 1)
        case _ if (list.head != prevElem) =>
          helper(
            list.tail,
            finalList :+ (prevElem, count),
            list.head,
            1
          )
        case _ =>
          helper(list.tail, finalList, list.head, count + 1)
      };
    };
    helper(list = list.tail, prevElem = list.head, count = 1);
  };
  println(compress(List('a', 'a', 'b', 'c', 'c', 'c', 'd', 'd', 'c')));
};
