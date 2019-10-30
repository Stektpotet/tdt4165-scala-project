import scala.collection.mutable

object TransactionStatus extends Enumeration {
  val SUCCESS, PENDING, FAILED = Value
}

class TransactionQueue {
  // project task 1.1
  // Add data structure to contain the transactions

  private val data = mutable.Queue[Transaction]()

  // Remove and return the first element from the queue
  def pop(): Transaction = this.synchronized {
    data.dequeue()
  }

  // Return whether the queue is empty
  def isEmpty: Boolean = this.synchronized {
    data.isEmpty
  }

  // Add new element to the back of the queue
  def push(t: Transaction): Unit = this.synchronized {
    data.enqueue(t)
  }

  // Return the first element from the queue without removing it
  def peek: Transaction = this.synchronized {
    data.head
  }

  // Return an iterator to allow you to iterate over the queue
  def iterator: Iterator[Transaction] = this.synchronized {
    data.iterator
  }

}

class Transaction(val transactionsQueue: TransactionQueue,
                  val processedTransactions: TransactionQueue,
                  val from: Account,
                  val to: Account,
                  val amount: Double,
                  val allowedAttempts: Int) extends Runnable {

  var status: TransactionStatus.Value = TransactionStatus.PENDING
  var attempt: Int = 0

  override def run(): Unit = {

    def doTransaction(): Unit = {
      val fromSuccess = from.withdraw(amount)
      fromSuccess match {
        case Left(_) =>
          // If from.withdraw succeeded, it means amount is positive. This means
          // to.deposit cannot fail, so we don't check if it fails
          to.deposit(amount)
          status = TransactionStatus.SUCCESS

        case Right(_) =>
          attempt += 1
          if (attempt >= allowedAttempts) {
            status = TransactionStatus.FAILED
          }
      }
    }

    if (status == TransactionStatus.PENDING) {
      doTransaction()
      Thread.sleep(50)
    }
  }

}
