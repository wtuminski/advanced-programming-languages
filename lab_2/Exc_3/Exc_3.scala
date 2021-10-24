import scala.annotation.tailrec
// Zadanie 3. Używając rekurencji ogonowej napisz funkcję
// def uporządkowana(tab: Array[Int], mlr: (Int, Int) => Boolean): Boolean
// która sprawdza, czy tablica liczb całkowitych będąca jej argumentem jest uporządkowana zgodnie z porządkiem definiowanym przez funkcję mlr.
// Rozwiąż to zadanie bez korzystania ze zmiennych.

object Main extends App {
  def uporządkowana(tab: Array[Int], mlr: (Int, Int) => Boolean): Boolean = {
    if (tab.length < 2) {
      println("Długość tablicy musi wynosić minimum dwa");
      return false;
    };
    @tailrec
    def helper(tab: Array[Int]): Boolean = {
      if (tab.length < 2) return true;
      if (!mlr(tab(0), tab(1))) return false;
      else helper(tab.tail);
    };
    return helper(tab);
  };

  def mlr(a: Int, b: Int): Boolean = { return a <= b };
  def printer(tab: Array[Int]): Unit = {
    val decision =
      if (uporządkowana(tab, mlr)) "upożądkowana"
      else "nieupożądkowana";
    println(s"Tablica [${tab.mkString(", ")}] jest $decision");
  }

  val arrayNr1 = Array(1, 2, 3, 4, 5);
  val arrayNr2 = Array(2, 3, 4, 1, 2, 3, 4);
  val arrayNr3 = Array(5, 7, 9, 10, 12, 12, 13);

  printer(arrayNr1);
  printer(arrayNr2);
  printer(arrayNr3);

};
