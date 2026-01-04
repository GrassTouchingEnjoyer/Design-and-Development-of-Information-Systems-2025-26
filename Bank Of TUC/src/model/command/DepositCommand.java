package model.command;

import model.actors.BankAccount.AccountStatus;
import model.actors.PersonalAccount;
import model.capitalTransfer.Transaction;
import model.capitalTransfer.TransactionBuilder;
import model.system_data.dao.TransactionDAO;


public class DepositCommand implements Command {

    private final PersonalAccount account;
    private final double amount;
    private final TransactionDAO transactionDAO;

    public DepositCommand(PersonalAccount account,
                          double amount,
                          TransactionDAO transactionDAO) {
        this.account = account;
        this.amount = amount;
        this.transactionDAO = transactionDAO;
    }

    @Override
    public CommandResult execute() {
    	if(account.getStatus().equals(AccountStatus.ACTIVE)) {
    		try {
        		account.deposit(amount);
        	} catch (RuntimeException e) {
                // Capture το exception και επιστρέφουμε status
                return new CommandResult(false, "Deposit failed: " + e.getMessage());
            }
            Transaction tx = TransactionBuilder.create()
    	            .from(account)
    	            .amount(amount)
    	            .type(Transaction.TransactionType.DEPOSIT)
    	            .build();


            transactionDAO.save(tx);
            return new CommandResult(true, "Deposit successful: " + amount + " €");
    	}else return new CommandResult(false, "Deposit failed: Account is blocked");
    	
    }
}
