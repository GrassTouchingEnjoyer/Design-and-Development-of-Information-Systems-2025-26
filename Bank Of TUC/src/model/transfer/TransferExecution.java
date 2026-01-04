package model.transfer;

import model.actors.BankAccount;
import model.actors.PersonalAccount;
import model.capitalTransfer.Transaction;

public interface TransferExecution {
    TransferResult execute(
        PersonalAccount from,
        String toIban,
        double amount
    );
    
    Transaction.TransactionType getTransactionType();
}
