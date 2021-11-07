// Zadanie 3. Zdefiniuj następujące generyczne operujące na funkcjach: Niech MSet[A] oznacza multi-zbiór (zbiór w którym elementy mogą się powtarzać) typu A.
// type MSet[A] = A => Int
// Czyli jest to funkcja zwracająca liczbę wystąpienia elementu typu A w danym multi-zbiórze, np.
// val a:MSet[Int] = (n: Int) => n match {
//  case 1 => 2
//  case 3 => 1
//  case _ => 0
// }.
// Zdefiniuj operacje: sumy, różnicy oraz części wspólnej dla multi-zbiórów:
// def plus[A](s1: MSet[A], s2: MSet[A]): MSet[A]
// def minus[A](s1: MSet[A], s2: MSet[A]): MSet[A]
// def częśćWspólna[A](s1: MSet[A], s2: MSet[A]): MSet[A]
// Wykorzystaj funkcje zdefiniowane w rozwiązaniu Zadania 2!
package Exc_3

import Exc_2.Main.{compose, prod, lift}

object Main extends App {
  type MSet[A] = A => Int;

  def plus[A](s1: MSet[A], s2: MSet[A]): MSet[A] = {
    lift((a: Int, b: Int) => a + b)(s1, s2);
  };

  def minus[A](s1: MSet[A], s2: MSet[A]): MSet[A] = {
    lift((a: Int, b: Int) => a - b)(s1, s2);
  };

  def intersection[A](s1: MSet[A], s2: MSet[A]): MSet[A] = {
    lift((a: Int, b: Int) =>
      a match {
        case 0 if (b == 0) => 0
        case _ if (b == 0) => 0
        case _ if (b != 0) => a + b
      }
    )(s1, s2);
  };

  val setA: MSet[Int] = (n: Int) =>
    n match {
      case 1 => 2
      case 3 => 1
      case _ => 0
    };

  val setB: MSet[Int] = (n: Int) =>
    n match {
      case 0 => 1
      case 1 => 2
      case 2 => 4
      case 3 => 7
      case _ => 0
    };
  println(plus(setA, setB)(5));
  println(minus(setA, setB)(3));
  println(intersection(setA, setB)(1))
  println(intersection(setA, setB)(5))

};
