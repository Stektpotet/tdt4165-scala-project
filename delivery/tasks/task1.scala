object task1 {
  def main(args: Array[String]): Unit = {
    taskA()
    taskB()
    taskC()
    taskD()
  }


  /**
   * Generates an array containing the values 1 up to and including 50 using a for loop.
   */
  def taskA(): Unit = {
    var arr = Array[Int]()
    for (i <- 0 to 50) {
      arr = arr :+ i
    }
    println("Task a): Generate array with values 1 to 50 using for loop")
    println(arr.mkString(" "))
    println()
  }


  /**
   * Sums the elements in an array of integers using a for loop.
   */
  def taskB(): Unit = {
    var arr = Array[Int]()
    for (i <- 0 to 50) {
      arr = arr :+ i
    }

    var sum = 0
    for (i <- arr) {
      sum += i
    }
    println("Task b): Sum integers in array using for loop")
    println("Sum: " + sum)
    println()
  }


  /**
   * Sums the elements in an array of integers using recursion.
   */
  def taskC(): Unit = {
    var arr = Array[Int]()
    for (i <- 0 to 50) {
      arr = arr :+ i
    }

    println("Task c): Sum integers in array using recursion")
    println("RecursiveSum: " + RecursiveSum(arr))
    println()
  }

  /**
   * Sums an array of ints recursively.
   *
   * @param arr the array of ints to sum.
   * @return the sum.
   */
  def RecursiveSum(arr: Array[Int]): Int = {
    if (arr.isEmpty) 0
    else arr.head + RecursiveSum(arr.tail)
  }


  /**
   * Computes the nth Fibonacci number using recursion.
   * The difference between int and BigInteger is that ints are represented by 32 bits, while
   * "BigInteger must support values in the range -2^Integer.MAX_VALUE (exclusive) to +2^Integer.MAX_VALUE (exclusive)
   * and may support values outside of that range".
   * (taken from Java 8 docs; https://docs.oracle.com/javase/8/docs/api/java/math/BigInteger.html)
   */
  def taskD(): Unit = {
    println("Task d): compute nth Fibonacci number using recursion")
    println("1st Fibonacci number: " + nthFibonacci(1))
    println("2nd Fibonacci number: " + nthFibonacci(2))
    println("50th Fibonacci number: " + nthFibonacci(50))
  }

  /**
   * Computes the nth Fibonacci number.
   *
   * @param count the nth number in the Fibonacci sequence to find.
   * @return the Fibonacci number.
   */
  def nthFibonacci(count: BigInt): BigInt = {
    helpFibonacci(0, 1, count)
  }

  /**
   * Helper function to find Fibonacci numbers using tail recursion.
   */
  @scala.annotation.tailrec
  def helpFibonacci(firstValue: BigInt, secondValue: BigInt, count: BigInt): BigInt = {
    if (count > 1) {
      val sum = firstValue + secondValue
      helpFibonacci(secondValue, sum, count - 1)
    }
    else
      secondValue
  }
}
