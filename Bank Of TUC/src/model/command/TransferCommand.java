package model.command;

import model.actors.BankAccount;
import model.actors.BankAccount.AccountStatus;
import model.actors.BusinessAccount;
import model.actors.PersonalAccount;
import model.capitalTransfer.Transaction;
import model.capitalTransfer.TransactionBuilder;
import model.system_data.dao.BusinessClientDAO;
import model.system_data.dao.PersonalAccountDAO;
import model.system_data.dao.TransactionDAO;
import model.transfer.TransferExecution;
import model.transfer.TransferResult;

public class TransferCommand implements Command {

    private final PersonalAccount from;
    private final String toIban;
    private final double amount;
    private final TransferExecution execution;
    private final TransactionDAO transactionDAO;
    private final PersonalAccountDAO pdao;
    private final BusinessClientDAO bdao;

    public TransferCommand(
            PersonalAccount from,
            String toIban,
            double amount,
            TransferExecution execution,
            TransactionDAO dao,
            PersonalAccountDAO pdao,
            BusinessClientDAO bdao) {

        this.from = from;
        this.toIban = toIban;
        this.amount = amount;
        this.execution = execution;
        this.transactionDAO = dao;
        this.pdao=pdao;
        this.bdao=bdao;
    }

    @Override
    public CommandResult execute() {
    	if(from.getStatus().equals(AccountStatus.ACTIVE)) {
    		
    		if(transferIsBlocked()) {
    			return new CommandResult(false, "Transfer failed: The account "+toIban+" is blocked");
    		}
    		
    		TransferResult result =
                    execution.execute(from, toIban, amount);

            if (!result.isSuccess()) {
                System.out.println("Transfer failed: " + result.getMessage());
                return new CommandResult(false, "Transfer failed: " + result.getMessage());
            } else {
            	Transaction tx = TransactionBuilder.create()
                        .from(from)
                        .to(toIban)
                        .amount(amount)
                        .type(execution.getTransactionType())
                        .build();

                transactionDAO.save(tx);
                
                BankAccount to = pdao.findByIban(toIban);

                if (to == null) {
                	to = bdao.findByIban(toIban);
                }
                if (to != null) {
                	to.addTransaction(tx);
                }
                return new CommandResult(true, "Transfer succesfull: ");
    	}
        }else return new CommandResult(false, "Transfer failed: Account is blocked");
        
    }
    
    public boolean transferIsBlocked() {
    	BankAccount b=bdao.findByIban(toIban);
    	if(b!=null) {
    		if(b.getStatus().equals(AccountStatus.BLOCKED) ) return true;
    	}else {
    		b=pdao.findByIban(toIban);
    		if(b!=null) {
        		if(b.getStatus().equals(AccountStatus.BLOCKED) ) return true;
        	}
    	}
    	return false;
    }
}

