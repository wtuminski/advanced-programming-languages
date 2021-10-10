// Zadanie 1. Zdefiniuj funkcję def obramuj(napis: String): String = { ... }, która umieszcza łańcuch znaków napis w ramce z gwiazdek. Załóż, że argument może zawierać ("linuksowe") znaki zmiany wiersza ('\n'), czyli być tekstem "wielolinijkowym".
// Scala umożliwia obliczenie długości dowolnego napisu (napis.length), a także "rozbicie" argumentu na tablicę linii (napis.split('\n')). Aby w tablicy znaleźć łańcuch o maksymalnej długości możemy wykorzystać tablica.maxBy(s => s.length) Uzyskaną w ten sposób tablicę możemy np. przekształcić na tablicę linijek w ramce (tablica.map(s => "* " + s + " *"))

object Main extends App {
  def wrap(baseText: String) = {
    val textRows = baseText.split("\\\\n");
    val lengthOfTheLongestRow = textRows.maxBy(row => row.length).length;
    val textRowsWithBorder = textRows.map(row =>
      "* " + row + " " * (lengthOfTheLongestRow - row.length) + " *"
    );

    println("*" * (lengthOfTheLongestRow + 4));
    textRowsWithBorder
      .foreach(println);
    println("*" * (lengthOfTheLongestRow + 4));
  }

  print("Provide a text: ")
  val text = io.StdIn.readLine();
  wrap(text);
};
