// Zadanie 6. Korzystając z "pętli" for/yield zdefiniuj funkcję:
// def threeNumbers(n: Int): Seq[(Int, Int, Int)] = /* ... */
// która wróci sekwencję zawierającą krotki trzech liczb: (a, b, c), liczby a, b, c, powinny być z przedziału [1, n] oraz są zgodne ze wzorem:
// a2 + b2 = c2, gdzie a < b.
// Podpowiedź: Sposób wygenerowania ciągu liczb od z przedziału [a, b]: a until b.
package Exc_6

object Main extends App {
  def threeNumbers(n: Int): Seq[(Int, Int, Int)] = {
    for (
      a <- 1 until n - 1;
      b <- 2 until n if a < b;
      c <- 3 until n + 1 if a < b && a + b == c
    )
      yield (a, b, c);
  };
  println(threeNumbers(25))
};
