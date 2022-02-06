import scala.annotation.tailrec

@main
def Zad1: Unit = {

 def counting[A](l: List[A]): Set[(A, Int)] = {
    @tailrec
    def helper(l: List[A], acc:  Map[A, Int] = Map()): Set[(A, Int)] = {
        l match {
            case Nil => acc.toSet
            case h::t => 
                val prevValue = acc.getOrElse(h, 0)
                helper(t, acc.updated(h, prevValue + 1))
        }
    }
    helper(l)
 }

 println(counting(List("a", "b", "a", "c", "a", "b", "a")))
 println(counting(List(1,2,3,4,1,3,1,5,6,1,2)))
}
