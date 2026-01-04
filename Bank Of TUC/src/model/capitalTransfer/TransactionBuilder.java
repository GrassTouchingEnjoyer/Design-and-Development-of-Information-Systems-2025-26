package model.capitalTransfer;

import model.actors.BankAccount;
import model.capitalTransfer.Transaction;
import model.capitalTransfer.Transaction.TransactionType;

import java.time.LocalDateTime;

public class TransactionBuilder {

    private String transactionId;
    private BankAccount fromAccount;
    private String toAccount;
    private double amount;
    private LocalDateTime timeOfExecution;
    private TransactionType type;

    public static TransactionBuilder create() {
        return new TransactionBuilder();
    }

    public TransactionBuilder transactionId(String transactionId) {
        this.transactionId = transactionId;
        return this;
    }

    public TransactionBuilder from(BankAccount fromAccount) {
        this.fromAccount = fromAccount;
        return this;
    }

    public TransactionBuilder to(String toAccount) {
        this.toAccount = toAccount;
        return this;
    }

    public TransactionBuilder amount(double amount) {
        this.amount = amount;
        return this;
    }

    public TransactionBuilder time(LocalDateTime time) {
        this.timeOfExecution = time;
        return this;
    }

    public TransactionBuilder type(TransactionType type) {
        this.type = type;
        return this;
    }

    public Transaction build() {
        Transaction tx = new Transaction(
                transactionId != null ? transactionId : java.util.UUID.randomUUID().toString(),
                fromAccount,
                toAccount,
                amount,
                timeOfExecution != null ? timeOfExecution : LocalDateTime.now()
        );
        tx.setType(type);
        return tx;
    }
}
