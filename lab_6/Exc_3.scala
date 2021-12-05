// Zadanie 3. Zapoznaj się oraz stwórz mechanizm tworzenia nowych obiektów bez konieczności pisania new.
// Przykładowo co powoduje, że działa przypisanie w formie: val c = C(1.0, 2.5)

package Exc_3

class C {
  var re: Double = _
  var im: Double = _
  override def toString =
    re + (if (im < 0) " - " + -im + "i" else if (im > 0) " + " + im + "i" else "")
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
def main() = {
    val myC = C(1,1)
    println(myC.toString)
}