class Account(val bank: Bank, initialBalance: Double) {

  class Balance(var amount: Double) {}

  val balance = new Balance(initialBalance)

  // for project task 1.2: implement functions
  // for project task 1.3: change return type and update function bodies
  def withdraw(amount: Double): Either[Unit, String] = this.synchronized {
    if (balance.amount < amount) {
      Right("Tried withdrawing more funds than available on account")
    }
    else if (amount < 0) {
      Right("Tried withdrawing negative funds")
    }
    else {
      balance.amount -= amount
      Left()
    }
  }

  def deposit(amount: Double): Either[Unit, String] = this.synchronized {
    if (amount < 0) {
      Right("Tried depositing negative funds")
    }
    else {
      balance.amount += amount
      Left()
    }
  }

  def getBalanceAmount: Double = this.synchronized {
    balance.amount
  }

  def transferTo(account: Account, amount: Double): Unit = {
    bank.addTransactionToQueue(this, account, amount)
  }

}
