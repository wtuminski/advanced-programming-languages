// Zadanie 1. Korzystając z metod oferowanych przez kolekcje zdefiniuj funkcję:
// def minNotContained(set: Set[Int]): Int = /* ... */
// która zwróci najmniejszą nieujemną liczbę całkowitą, która nie występuje w zbiorze set.
// Przykład:
// Dla: set = Set(-3, 0, 1, 2, 5, 6), funkcja powinna zwrócić: 3.

object Main extends App {
  def minNotContained(set: Set[Int]): Int = {
    val minInt = set.toList
      .filter(a => a >= 0)
      .sortWith((a, b) => a <= b)
      .zipWithIndex
      .foldLeft(-1)((acc, current) =>
        if (current._1 != current._2 && acc == -1) current._2
        else acc
      );
    return if (minInt >= 0) minInt else if (set.max >= 0) set.max + 1 else 0;
  }
  val set = Set(-3, 0, 1, 2, 5, 6);
  val set2 = Set(-3, -1, -2);
  println(minNotContained(set))
}
