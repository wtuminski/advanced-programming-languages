// Zadanie nr 2
// 	Na podstawie klasy abstrakcyjne i klas pochodnych znajdującą się w pliku myTreeDemo.zip zaprojektuj własne binarne drzewo poszukiwań.
// Dokonaj odpowiednich modyfikacji, które spowodują, że puste drzewo będzie tylko jedno (będzie miało tylko jedną instancję). Możesz dodać i zmienić powyższy kod oraz dodać nowe własności. Oczywiście API (czyli udostępniane użytkownikowi metody) powinno pozostać bez zmian.
// Uwaga: Przedstawione API, (dla uproszczenia) dobiera pozycję umieszczenia elementów wg wartości zwracanej przez metodę hashCode, normalnie powinno się użyć Ordered.
// Podpowiedź: Dla dowolnego typu X w Scali mamy "Nothing <: X"

// Zadanie nr 3
// Dodaj możliwość definiowania drzew.
// MyTree()
// MyTree(1, 2, 3)
// Podpowiedź: Pobranie dowolnej liczby argumentów we funkcji możesz uzyskać poprzez dodanie * po określeniu typu, np. funk(arg: Int*)

// Zadanie 4 
// Zmodyfikuj klasę drzew tak, aby reprezentacja tekstowa jej obiektów była bardziej czytelna:
// {element1, element2, ..., elementN}
// Dane powinny być wypisane zgodnie z porządkiem in-order.

package Exc_2_4

abstract class MyTree[+A] {
  def key: A
  def left: MyTree[A]
  def right: MyTree[A]
  def isEmpty: Boolean
  def insert[B >:A](k: B): MyTree[B]
  def find[B >: A](k: B): Boolean
}

protected object EmptyTree extends MyTree[Nothing] { 
  def key: Nothing = throw new NoSuchElementException
  def left: MyTree[Nothing] = throw new NoSuchElementException
  def right: MyTree[Nothing] = throw new NoSuchElementException
  def isEmpty: Boolean = true
  def insert[A >: Nothing](k: A): MyTree[A] = Node(k, EmptyTree, EmptyTree)
  def find[A >: Nothing](k: A): Boolean = false

  // exc 4 - part I
  override def toString = "''"
}

protected class Node[A](val key: A, val left: MyTree[A], val right: MyTree[A])  extends MyTree[A] {
  // exc 3
  def this() = this((math.random * 100).toInt.asInstanceOf[A], EmptyTree, EmptyTree)
  def this(key: A) = this(key, EmptyTree, EmptyTree)
  def this(key: A, left: A, right: A) = this(key, Node(left), Node(right))
  def isEmpty: Boolean = false
  def insert[B >: A](k: B): MyTree[B] = {
    if (key.hashCode > k.hashCode) new Node(key, left.insert(k), right)
    else new Node(key, left, right.insert(k))
  }
  def find[B >: A](k: B): Boolean = {
    if (key == k) true
    else if (key.hashCode > k.hashCode) left.find(k)
    else right.find(k)
  }

  // exc 4 - part II
  override def toString = s"$key -> left: ${left.toString}, right: ${right.toString}"
}


@main
def main() = {
    val emptyTree = EmptyTree
    println(s"emptyTree.isEmpty -> ${emptyTree.isEmpty}")
    println(s"emptyTree.insert('A') -> ${emptyTree.insert('A')}")
    println(s"emptyTree.find('A') -> ${emptyTree.find('A')}\n")

    val normalTree = Node(1, Node(2, EmptyTree, EmptyTree), EmptyTree)
    println(s"normalTree.isEmpty => ${normalTree.isEmpty}")
    println(s"normalTree.key => ${normalTree.key}")
    println(s"normalTree.left => ${normalTree.left}")
    println(s"normalTree.right => ${normalTree.right}\n")

    println("Exc nr 3")
    val treeWithEmptyParms = Node()
    println(s"normalTree() => ${treeWithEmptyParms}")
    println(s"normalTree().key => ${treeWithEmptyParms.key}")
    val treeWithInts = Node(1,2,3)
    println(s"normalTree(1,2,3) => ${treeWithInts}")
    println(s"normalTree(1,2,3).key => ${treeWithInts.key}\n")

    println("Exc nr 4")
    println(s"emptyTree.toString -> ${emptyTree.toString}")
    println(s"normalTree.toString -> ${treeWithInts.toString}")
}