// Zadanie 1. Zdefiniuj klasę C, która będzie reprezentowała liczby zespolone, która będzie zawierała pola re i im reprezentujące część rzeczywistą i urojoną liczby. Utwórz odpowiedni konstruktor.
package Exc_1
class C(val re: Double, val im: Double) {
}

@main 
def main() ={
    val myC = C(1,1)
    println(myC)
}