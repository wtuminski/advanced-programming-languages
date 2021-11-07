// Zadanie 2. Zdefiniuj następujące generyczne operacje na funkcjach

//     "złożenie"
//     def compose[A,B,C](f: A => B)(g: B => C): A => C
//     "iloczyn"
//     def prod[A,B,C,D](f: A => C, g: B => D): (A, B) => (C, D)
//     "podniesienie operatora"
//     def lift[A,T](op: (T,T) => T)(f: A => T, g: A => T): A => T

object Main extends App {
  def compose[A, B, C](f: A => B)(g: B => C): A => C = { (a: A) =>
    (g(f(a)))
  }

  def prod[A, B, C, D](f: A => C, g: B => D): (A, B) => (C, D) = {
    (a: A, b: B) => (f(a), g(b))
  }

  def lift[A, T](op: (T, T) => T)(f: A => T, g: A => T): A => T = { (a: A) =>
    op(f(a), g(a))
  }
}
