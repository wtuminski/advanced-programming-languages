// Dodaj możliwość wykonywania operacji arytmetycznych ze w zwykłymi liczbami rzeczywistymi.
// Przykładowo powinny działać operacje:
// 5.3 + C(2.1, 3.5)
// C(2.2, 3.4) * 2.5

package Exc_7

class C(val re: Double, val im: Double) {
  def this(re: Double) = this(re, 0);
  override def toString =
    re + (if (im < 0) " - " + -im + "i" else if (im > 0) " + " + im + "i" else "")

  def +(that: C) = C(re + that.re, im + that.im)
  def -(that: C) = C(re - that.re, im - that.im)
  def *(that: C) = C(re * that.re - im * that.im, (re * that.im + im * that.re))
  def /(that: C) = {
      if(that.im == 0 || that.re == 0) throw new IllegalArgumentException("cannot divide per 0")
      val newRe = (re * that.re + im * that.im) / (Math.pow(that.re, 2) + Math.pow(that.im, 2))
      val newIm = (that.re * im - re * that.im) / (Math.pow(that.re, 2) + Math.pow(that.im, 2))
      C(newRe, newIm)
  }
}

object C {
  implicit def fromDouble(d: Double): C = new C(d)
}

@main
def main() = {
    println(s"5.3 + C(2.1, 3.5): ${5.3 + C(2.1, 3.5)}")
    println(s"C(2.2, 3.4) * 2.5: ${C(2.2, 3.4) * 2.5}")
    println(s"2 / C(2.2, 3.4): ${2 / C(2.2, 3.4)}")
}