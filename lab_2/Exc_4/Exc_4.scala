import scala.annotation.tailrec
// Zadanie 4. Zdefiniuj rekurencyjną funkcję ciąg(n: Int): Int, która zwróci n-ty wyrażony wzorem:
// F(0) = 1
// F(1) = 1
// F(n) = F(n-1) + F(n-2) dla n > 1
// Rozwiąż to zadanie bez korzystania ze zmiennych oraz wykorzystaj rekurencję ogonową.

object Main extends App {
  def ciąg(n: Int): Int = {
    @tailrec
    def helper(n: Int, a: Int = 0, b: Int = 1): Int = {
      if (n == 0) b;
      else helper(n - 1, b, a + b);
    };
    return helper(n);
  };

  print(ciąg(5))
};
