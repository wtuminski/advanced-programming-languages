// Zadanie 1. Korzystając z metod drop i take zdefiniuj funkcję:
// def subSeq[A](seq: Seq[A], begIdx: Int, endIdx: Int): Seq[A] = /* ... */
// która zwraca podciąg ciągów sekwencji seq z przedziału od indeksu begIdx do endIdx.
package Exc_1
object Main extends App {
  def subSeq[A](seq: Seq[A], begIdx: Int, endIdx: Int): Seq[A] = {
    seq.drop(begIdx).take(endIdx - begIdx);
  };
  println(subSeq(Seq(0, 1, 2, 3, 4, 5, 6, 7, 8, 9), 3, 5));
};
