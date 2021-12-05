//  Stwórz metodę /(that: C), która umożliwią wykonanie operacji arytmetycznej na liczbach zespolonych. Jeżeli podany argument będzie powodował dzielenie przez 0, powinien zostać uruchomiony wyjątek IllegalArgumentException. Wywołaj metodę i obsłuż odpowiednio wyjątek.

package Exc_5

class C(val re: Double, val im: Double) {
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
   val myC = C(1,2)
    println(s"C(1,2) / C(5,6): ${(myC / C(5,6)).toString}")
    try{
        println(s"C(1,2) / C(0,4) - exception: ${(myC / C(0,4)).toString}")
    } catch {
        case e => println(e)
    }
    
}