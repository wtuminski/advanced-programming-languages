// Zadanie 4. Stwórz metody +(that: C), -(that: C), *(that: C), które umożliwią wykonanie operacji arytmetycznych na liczbach zespolonych.

package Exc_4

class C(val re: Double, val im: Double) {
  def +(that: C) = C(re + that.re, im + that.im)
  def -(that: C) = C(re - that.re, im - that.im)
  def *(that: C) = C(re * that.re - im * that.im, (re * that.im + im * that.re))
  override def toString =
    re + (if (im < 0) " - " + -im + "i" else if (im > 0) " + " + im + "i" else "")
}

@main 
def main() = {
    val myC = C(1,2)
    println(s"C(1,2) + C(5,6): ${(myC + C(5,6)).toString}")
    println(s"C(1,2) - C(5,6): ${(myC - C(5,6)).toString}")
    println(s"C(1,2) * C(5,6): ${(myC * C(5,6)).toString}")
}