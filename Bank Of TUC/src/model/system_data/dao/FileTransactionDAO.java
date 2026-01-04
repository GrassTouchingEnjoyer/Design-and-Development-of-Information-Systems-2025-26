package model.system_data.dao;

import java.util.List;
import java.util.stream.Collectors;
import model.capitalTransfer.Transaction;
import model.system_data.persistence.FileDataStore;
import model.actors.BankAccount;


public class FileTransactionDAO implements TransactionDAO {

    private final List<Transaction> transactions;

    public FileTransactionDAO() {
        this.transactions = FileDataStore.getInstance().getTransactions();
    }

    @Override
    public void save(Transaction tx) {
        // 1. Προσθήκη στο account
        tx.getFromAccount().addTransaction(tx);

        // 2. Προσθήκη στη master λίστα του FileDataStore
        FileDataStore.getInstance().getTransactions().add(tx);
    }
    
    @Override
    public void delete(Transaction tx) {
        // 1. Αφαίρεση από account
        tx.getFromAccount().removeTransaction(tx);

        // 2. Αφαίρεση από master list
        FileDataStore.getInstance().getTransactions().remove(tx);
    }

    @Override
    public List<Transaction> findAll() {
        return List.copyOf(transactions);
    }

    @Override
    public List<Transaction> findByFromAccount(BankAccount account) {
        return transactions.stream()
                .filter(t -> t.getFromAccount().equals(account))
                .collect(Collectors.toList());
    }

    @Override
    public List<Transaction> findByType(Transaction.TransactionType type) {
        return transactions.stream()
                .filter(t -> t.getType() == type)
                .collect(Collectors.toList());
    }
}

