// Zadanie 3. Korzystając z ciągu wszystkich stref czasowych (postaci Kontynent/Strefa):
// val strefy: Seq[String] = java.util.TimeZone.getAvailableIDs.toSeq
// oraz operacji na ciągach i zasugerowanej we wskazówce operacji stripPrefix na napisach, wyszukaj strefy znajdujące się w Europie i posortuj rosnąco ich nazwy względem długości. Strefy, których nazwy mają taką samą długość posortuj w kolejności alfabetycznej.
// Podpowiedź: wykorzystaj między innymi metody: map, filter oraz standardową operację na napisach:
// def stripPrefix(prefix: String): String
// która usuwa podany prefiks z napisu, np.
// "ala ma kota".stripPrefix("ala ") -> "ma kota"
package Exc_3;

import scala.util.matching.Regex

object Main extends App {
  def getEuropeanZones(): Seq[String] = {
    val prefix = "Europe/"
    val strefy: Seq[String] = java.util.TimeZone.getAvailableIDs.toSeq
    strefy
      .filter(strefa => strefa.contains(prefix))
      .map(strefa => strefa.stripPrefix(prefix))
      .sortWith((a, b) => a.length > b.length)
      .sortWith((a, b) => a.length == b.length && a < b)
  }
  println(getEuropeanZones())
}
