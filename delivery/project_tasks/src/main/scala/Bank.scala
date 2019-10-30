class Bank(val allowedAttempts: Integer = 3) {

  private val transactionsQueue: TransactionQueue = new TransactionQueue()
  private val processedTransactions: TransactionQueue = new TransactionQueue()

  // project task 2
  // create a new transaction object and put it in the queue
  // spawn a thread that calls processTransactions
  def addTransactionToQueue(from: Account, to: Account, amount: Double): Unit = {
    val transaction = new Transaction(transactionsQueue, processedTransactions, from, to, amount, allowedAttempts)
    transactionsQueue.push(transaction)
    Main.thread(processTransactions())
  }

  // project task 2
  @scala.annotation.tailrec
  private def processTransactions(): Unit = {
    val transaction = transactionsQueue.pop()
    // Since we would have to wait for the transaction to finish to check its status,
    // there's no point in spawning a thread and just waiting for it to finish.
    // This function (processTransactions) runs on a thread anyway
    transaction.run()
    if (transaction.status == TransactionStatus.PENDING) {
      transactionsQueue.push(transaction)
      processTransactions()
    }
    else {
      processedTransactions.push(transaction)
    }
  }

  def addAccount(initialBalance: Double): Account = {
    new Account(this, initialBalance)
  }

  def getProcessedTransactionsAsList: List[Transaction] = {
    processedTransactions.iterator.toList
  }

}
