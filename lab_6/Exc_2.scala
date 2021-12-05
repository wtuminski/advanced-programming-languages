// Skonstruuj klasę liczb zespolonych tak, aby reprezentacja tekstowa jej obiektów była bardziej czytelna:

//     dla b>0: a + bi
//     dla b<0: a - bi
//     dla b=0: a

package Exc_2

class C(val re: Double, val im: Double) {
  override def toString =
    re + (if (im < 0) " - " + -im + "i" else if (im > 0) " + " + im + "i" else "")
}

@main
def main() = {
    val C1 = C(1,5)
    val C2 = C(2,-4)
    val C3 = C(3,0)
    println(s"b>0: ${C1.toString}")
    println(s"b<0: ${C2.toString}")
    println(s"b=0: ${C3.toString}")
}
