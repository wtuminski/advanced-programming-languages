//  Zdefiniuj klasę Point, która będzie reprezentowała punkty w przestrzeni ℝ2 oraz będzie zawierała pola reprezentujące współrzędnych x, y. Stwórz możliwość wykonywania operatorów logicznych: ==, !=, <, >, <=, >=, które będą zwracały wartości zgodne z odległością punktów od współrzędnej (0, 0) na osi XY.
// Wykorzystaj cechę Ordered[A], tam gdzie to możliwe.

package Exc_1

class Point(val x: Float, val y: Float) extends Ordered[Point] {
  private def distanceFromStart = math.sqrt(math.pow(-x, 2) + math.pow(-y, 2))
  def compare(other: Point) = {
      val diffrence = distanceFromStart - other.distanceFromStart
      if(diffrence == 0) 0
      else if (diffrence > 0) 1
      else -1
  }
  def ==(other: Point) = x == other.x && y == other.y 
  def !=(other: Point) = x != other.x || y != other.y 
}

@main
def main() = {
    val firstPoint = Point(2, 1)
    val secondPoint = Point(0, 0)
    println("firstPoint: (2, 1), secondPoint: (0,0)")
    println(s"==: ${firstPoint == secondPoint}")
    println(s"!=: ${firstPoint != secondPoint}")
    println(s">: ${firstPoint > secondPoint}")
    println(s">=: ${firstPoint >= secondPoint}")
    println(s"<: ${firstPoint < secondPoint}")
    println(s"<=: ${firstPoint <= secondPoint}")
}
