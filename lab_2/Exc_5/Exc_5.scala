import scala.annotation.tailrec
// Zadanie 5. Napisz funkcję daSię(n: Int): Boolean, która dla argumentu n będącego liczbą naturalną (tzn. n >= 0) sprawdza, czy każdą parzystą liczbę naturalną z przedziału (2..n] da się przedstawić jako sumę dwóch liczb pierwszych. Dla każdej ze sprawdzanych liczb funkcja powinna wypisać w konsoli znaleziony "rozkład".
// Rozwiąż to zadanie bez korzystania ze zmiennych.

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

  def daSię(n: Int): Boolean = {
    val baseNumber = n;
    if (n <= 2) {
      println("Wprowadzona liczba musi spełniać warunek: n > 2");
      return false;
    };
    @tailrec
    def helper(n: Int, a: Int = 0): Boolean = {
      println(s"Sprawdzane liczby: $n, $a");
      if (jestPierwsza(n) && jestPierwsza(a)) {
        println(
          s"Liczbę $baseNumber można przedstawić jako sumę dwóch liczb pierwszych np.: $baseNumber = $n + $a"
        );
        return true;
      } else if (n == 0) {
        println(
          s"Liczba $baseNumber nie jest przedstawialna jako sumwa dwóch liczb pierwszych"
        )
        return false
      };
      else helper(n - 1, a + 1);
    };
    return helper(n);
  };

  daSię(18)
};
