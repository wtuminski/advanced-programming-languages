
case class Baby(
  firstName: String,
  year: Int,
  month: Int,
  day: Int,
  motherId: Int
)

@main
def Zad2: Unit = {	
 val newBorns = io.Source
  .fromResource("noworodki.txt")
  .getLines
  .toList
  .map(n => {
    val data = n.split(" ")
    val date = data(1).split("-")
    Baby(data.head, date.head.toInt, date(1).toInt, date.last.toInt, data.last.toInt)
  })

  val groupedNewBorns = newBorns.foldLeft(Map[String, Int]())((acc, n) => {
    val key = s"${n.year}-${n.month}-${n.day}-${n.motherId}"
    val prevValue = acc.getOrElse(key, 0)
    acc.updated(key, prevValue + 1)
  })
  val multiples = groupedNewBorns.filter(i => i._2 >= 2)
  
  val monthsWithMultiple = multiples.foldLeft(Map[Int, Int]())((acc, n) => {
    val month = n._1.split("-")(1).toInt
    val prevValue = acc.getOrElse(month, 0)
    acc.updated(month, prevValue + 1)
  })

  val months = List.tabulate(12)(i=>1).zipWithIndex.map(i=> {
    val month = i._1 * i._2 + 1
    val multiplesInMonth = monthsWithMultiple.getOrElse(month, 0)
    (month, multiplesInMonth)
  })
  println(months)
}
