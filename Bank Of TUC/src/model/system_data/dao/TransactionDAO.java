package model.system_data.dao;

import java.util.List;
import model.capitalTransfer.Transaction;
import model.actors.BankAccount;

public interface TransactionDAO {

    void save(Transaction transaction);

    List<Transaction> findAll();

    List<Transaction> findByFromAccount(BankAccount account);

    List<Transaction> findByType(Transaction.TransactionType type);

	void delete(Transaction tx);
}

