import scala.annotation.tailrec
// Zadanie 2. Napisz funkcję jestPierwsza(n: Int): Boolean która sprawdza, czy argument jest liczba pierwszą. Rozwiąż to zadanie bez korzystania ze zmiennych oraz wykorzystaj rekurencję ogonową.

object Main extends App {
  def jestPierwsza(n: Int): Boolean = {
    @tailrec
    def helper(n: Int, acc: Int = 2): Boolean = {
      if (n == acc) true;
      else if (n % acc == 0) false;
      else return helper(n, acc + 1);
    };

    return helper(n);
  };

  println("Czy 2 to liczba pierwsza: " + jestPierwsza(2))
  println("Czy 5 to liczba pierwsza: " + jestPierwsza(5))
  println("Czy 12 to liczba pierwsza: " + jestPierwsza(12))
  println("Czy 13 to liczba pierwsza: " + jestPierwsza(13))
  println("Czy 23 to liczba pierwsza: " + jestPierwsza(23))

};
