package model.transfer;

import model.actors.BankAccount;
import model.actors.PersonalAccount;
import model.capitalTransfer.Transaction;

public abstract class ExternalTransferExecution implements TransferExecution {

	protected final double fee;
	
	protected ExternalTransferExecution(double fee) {
	this.fee = fee;
	}
	
	@Override
	public TransferResult execute(
	    PersonalAccount from,
	    String toIban,
	    double amount) {
	
	if (from.getBalance() < amount + fee) {
	    return TransferResult.failure("Insufficient funds");
	}
	TransferResult res = sendExternal(from, toIban, amount);
	if(res.isSuccess()) {
		// χρέωση
		from.setBalance(from.getBalance() - amount - fee);
	}
	
	// εξωτερική αποστολή
	return res;
	}
	
	@Override
	public Transaction.TransactionType getTransactionType() {
	return Transaction.TransactionType.EXTERNAL_TRANSFER;
	}
	
	protected abstract TransferResult sendExternal(
	    PersonalAccount from,
	    String toIban,
	    double amount
	);
}

