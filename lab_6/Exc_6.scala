// Zdefiniuj konstruktor pomocniczy dzięki, któremu będziesz mógł przypisać jedynie wartość części rzeczywistej (pierwszy argument) tworząc obiekt.
package Exc_6

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

@main 
def main() = {
    val onlyRe = C(5)
    println(s"Only real number: ${C(2).toString}")
}