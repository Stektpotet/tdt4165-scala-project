class Bank(val allowedAttempts: Integer = 3) {

  private val transactionsQueue: TransactionQueue = new TransactionQueue()
  private val processedTransactions: TransactionQueue = new TransactionQueue()

  // project task 2
  // create a new transaction object and put it in the queue
  // spawn a thread that calls processTransactions
  def addTransactionToQueue(from: Account, to: Account, amount: Double): Unit = {
    val transaction = new Transaction(transactionsQueue, processedTransactions, from, to, amount, allowedAttempts)
    transactionsQueue.push(transaction)
    Main.thread(processTransactions)
  }

  // TODO
  // project task 2
  // Function that pops a transaction from the queue
  // and spawns a thread to execute the transaction.
  // Finally do the appropriate thing, depending on whether
  // the transaction succeeded or not
  private def processTransactions: Unit = {
    transactionsQueue.iterator.foreach(t =>t.run())

    while (!transactionsQueue.isEmpty) {
      println("BANK: 27 - pop the queue")
      val transaction = transactionsQueue.pop
      if (transaction.status == TransactionStatus.PENDING) {
        println("BANK: 30 - still pending, push back!")
        transactionsQueue.push(transaction)
        //TODO ??? maybe count attempts?
        processTransactions
      } else {
        processedTransactions.push(transaction)
      }

    }

  }

  def addAccount(initialBalance: Double): Account = {
    new Account(this, initialBalance)
  }

  def getProcessedTransactionsAsList: List[Transaction] = {
    processedTransactions.iterator.toList
  }

}
