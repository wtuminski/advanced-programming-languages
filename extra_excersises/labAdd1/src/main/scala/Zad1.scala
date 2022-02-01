// Zadanie 1. Używając kolekcji i operacji na nich oraz danych z wyników wyborów zawartych w pliku wybory.csv znajdź województwo, w którym różnica procentowa głosów oddanych na Koalicję Obywatelską oraz na PiS była minimalna. Otrzymane wyniki wyświetl na konsoli – zarówno dane województw(a), jak i wartości procentowe. Zauważ, że elementy o wartości "minimalnej/maksymalnej" nie muszą być unikatowe.
// Do reprezentowania danych o województwach i gminach użyj zdefiniowanych w pliku Zad1.scala "klas wzorcowych" (case class) Województwo i Wynik.

import scala.annotation.tailrec

@main
def zad1: Unit = {
  case class Województwo(nazwa: String, min: Int)
  // max ID gminy z województwa w: w.min + 19999
  case class Wynik(
    ID: Int,
    KOALICJA_EUROPEJSKA: Int,
    LEWICA_RAZEM: Int,
    POLEXIT: Int,
    JEDNOŚĆ_NARODU: Int,
    PIS: Int,
    EUROPA_CHRISTI: Int,
    WIOSNA: Int,
    KONFEDERACJA: Int,
    KUKIZ15: Int,
    POLSKA_FAIR_PLAY: Int
  ){
    override def toString() = {
      s"KOALICJA_EUROPEJSKA: ${this.KOALICJA_EUROPEJSKA}\n" +
        s"LEWICA_RAZEM: ${this.LEWICA_RAZEM}\n" +
        s"POLEXIT: ${this.POLEXIT}\n" +
        s"JEDNOŚĆ_NARODU: ${this.JEDNOŚĆ_NARODU}\n" +
        s"PIS: ${this.JEDNOŚĆ_NARODU}\n" +
        s"EUROPA_CHRISTI: ${this.EUROPA_CHRISTI}\n" +
        s"WIOSNA: ${this.WIOSNA}\n" +
        s"KONFEDERACJA: ${this.KONFEDERACJA}\n" +
        s"KUKIZ15: ${this.KUKIZ15}\n" +
        s"POLSKA_FAIR_PLAY: ${this.POLSKA_FAIR_PLAY}\n"
    }
  }

  case class PisPoScoresInPercent(
    PIS: Float,
    KOALICJA_EUROPEJSKA: Float,
    diff: Float
  )

  val województwa = List(
        Województwo("dolnośląskie",20000),
        Województwo("kujawsko-pomorskie",40000),
        Województwo("lubelskie",60000),
        Województwo("lubuskie",80000),
        Województwo("łódzkie",100000),
        Województwo("małopolskie",120000),
        Województwo("mazowieckie",140000),
        Województwo("opolskie",160000),
        Województwo("podkarpackie",180000),
        Województwo("podlaskie",200000),
        Województwo("pomorskie",220000),
        Województwo("śląskie",240000),
        Województwo("świętokrzyskie",260000),
        Województwo("warmińsko-mazurskie",280000),
        Województwo("wielkopolskie",300000),
        Województwo("zachodniopomorskie",320000)
      )

  val wyniki = io.Source
    .fromResource("wyniki.csv")
    .getLines
    .toList
    .map(l => {
    l.split(",").toList.map(_.toInt) match {
      case List(a,b,c,d,e,f,g,h,i,j,k) => Wynik(a,b,c,d,e,f,g,h,i,j,k)
      case _ => throw new IllegalArgumentException
    }
  })

  def calculateTotalInState(score: Wynik): Int = { 
    val scoreList = score.productIterator.toList.tail.map(s=>s.toString.toInt)
    scoreList.reduce((score, acc)=> acc + score)
  }

  def sumScores(a: Wynik | Null, b: Wynik, stateMin: Int) = {
    val firstScore = if a != null then a else Wynik(stateMin, 0,0,0,0,0,0,0,0,0,0)
    Wynik(
      firstScore.ID,
      firstScore.KOALICJA_EUROPEJSKA + b.KOALICJA_EUROPEJSKA,
      firstScore.LEWICA_RAZEM + b.LEWICA_RAZEM,
      firstScore.POLEXIT + b.POLEXIT,
      firstScore.JEDNOŚĆ_NARODU + b.JEDNOŚĆ_NARODU,
      firstScore.PIS + b.PIS,
      firstScore.EUROPA_CHRISTI + b.EUROPA_CHRISTI,
      firstScore.WIOSNA + b.WIOSNA,
      firstScore.KONFEDERACJA + b.KONFEDERACJA,
      firstScore.KUKIZ15 + b.KUKIZ15,
      firstScore.POLSKA_FAIR_PLAY + b.POLSKA_FAIR_PLAY
    )
  }

  @tailrec
  def getGroupedScores(states: List[Województwo], scores: List[Wynik], acc: List[(Województwo, Wynik)] = Nil): List[(Województwo, Wynik)] 
    = {
    if(states.isEmpty) return acc
    scores match {
      case Nil => acc
      case h::t if(h.ID >= states.head.min && (states.length < 2 || h.ID < states(1).min )) => 
        val firstScore = if acc.isEmpty then null else acc.head._2
        getGroupedScores(states, t, (states.head, sumScores(firstScore, h, states.head.min))::(if acc.isEmpty then acc else acc.tail))
      case h::t if(h.ID >= states(1).min) => 
        getGroupedScores(states.tail, t, (states.head, sumScores(null, h, states(1).min))::acc)
      case _ => throw new IllegalArgumentException("There aren't scores for all states")
    }
  }
  
  val groupedScores = getGroupedScores(województwa, wyniki)
  val sortedPisAndPoScores = groupedScores
    .map((a,b) => 
      val totalInState = calculateTotalInState(b).toFloat
      val pis = b.PIS.toFloat / totalInState
      val po = b.KOALICJA_EUROPEJSKA.toFloat / totalInState
      (a, PisPoScoresInPercent(pis, po, Math.abs(pis-po)))
    )
    .sortBy((a,b)=> b.diff)
  val bestPisAndPoScores = sortedPisAndPoScores.filter((a,b)=> b.diff == sortedPisAndPoScores.head._2.diff)

  println("\nSearched states:\n")
  
  bestPisAndPoScores.foreach(item => 
    println(s"${item._1.nazwa}:\n")
    println(s"PIS: ${(item._2.PIS * 10000.0).round.toFloat / 100.0}%")
    println(s"KOALICJA EUROPEJSKA: ${(item._2.KOALICJA_EUROPEJSKA * 10000.0).round.toFloat / 100.0}%\n")
    println(s"All scores: \n${groupedScores.find((state,_) => state.nazwa == item._1.nazwa).toList.head._2}\n")
  )
}

 

