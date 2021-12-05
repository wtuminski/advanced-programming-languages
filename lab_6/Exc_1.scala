// Zadanie 1. Zdefiniuj klasę C, która będzie reprezentowała liczby zespolone, która będzie zawierała pola re i im reprezentujące część rzeczywistą i urojoną liczby. Utwórz odpowiedni konstruktor.
package Exc_1
class C {
  var re: Double = _
  var im: Double = _
}

object C {
  def apply(re: Double, im: Double): C = {
    val c = new C
    c.re = re
    c.im = im
    c
  }
}

@main 
def main() ={
    val myC = C(1,1)
    println(myC)
}