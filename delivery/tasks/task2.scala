import java.util.concurrent.atomic.AtomicInteger
import scala.concurrent.duration._
import scala.concurrent.Await

object task2 {

  /**
   * Create a Thread object wrapping the function passed in.
   *
   * @param  f
   * the function to run { @code run} method is invoked when this thread
   * is started. If { @code null}, this classes { @code run} method does
   * nothing.
   */
  def Threaded(f: => Unit) = new Thread(() => f)

  def main(args: Array[String]): Unit = {
    task2a()
    task2b()
    task2c()
    task2d()
  }

  def task2a(): Unit = {
    print("===============================================================================\n" +
      "Task2\ta.\nMake a threaded variant of the function passed\n" +
      "val hello = Threaded(() => println(\"Hello, World!\")\n" +
      "hello.start()\n\n" +
      ">\t")
    val hello = Threaded(() => println("Hello, World!"))
    hello.start()
    hello.join()
  }

  def task2b(): Unit = {
    print("===============================================================================\n" +
      "Task2\tb.\nRunning the following may cause printing of 0, 1 or 2,\n" +
      "depending on which thread gets to run first\n" +
      "\tThreaded(increaseCounter).start()\n\tThreaded(printCounter).start()\n\tThreaded(increaseCounter).start()\n" +
      ">\t")
    var counter: Int = 0

    def increaseCounter(): Unit = {
      counter += 1
    }

    def printCounter(): Unit = {
      println(counter)
    }

    val threads = Array(Threaded(increaseCounter), Threaded(printCounter), Threaded(increaseCounter))
    threads.foreach(t => t.start())
    threads.foreach(t => t.join())
  }

  def task2c(): Unit = {
    print("===============================================================================\n" +
      "Task2\tc.\n" +
      "There were two easy ways of implementing increaseCounter thread-safely:\n" +
      "\t1. Change the signature of incrementCounter to\n" +
      "\t\tdef increaseCounter(): Unit = this.synchronized { counter += 1 }\n" +
      "\t2. Use AtomicInteger instead of Int.\n" +
      ">\t")
    val counter = new AtomicInteger(0)

    def increaseCounter(): Unit = this.synchronized {
      counter.incrementAndGet()
    }

    def printCounter(): Unit = {
      println(counter)
    }

    val threads = Array(Threaded(increaseCounter), Threaded(printCounter), Threaded(increaseCounter))
    threads.foreach(t => t.start())
    threads.foreach(t => t.join())
  }

  def task2d(): Unit = {
    print("===============================================================================\n" +
      "Task2\td.\n" +
      "Depending on how much time each thread gets to execute, this may or may not cause\n" +
      "a deadlock. A deadlock occurs in this code through the following sequence:\n" +
      "1.\tThread A and B starts\n" +
      "2.\tA- and B-accessing of lazy val -> locks access to their respective objects\n" +
      "3.\tA tries to access locked B, B tries to access locked B\n" +
      "4.\tDEADLOCK!!!\n")
    object A {
      lazy val base = 42
      lazy val start = B.step
    }
    object B {
      lazy val step = A.base
    }
    Threaded(() => println(A.start)).start()
    Threaded(() => println(B.step)).start()
  }
}
