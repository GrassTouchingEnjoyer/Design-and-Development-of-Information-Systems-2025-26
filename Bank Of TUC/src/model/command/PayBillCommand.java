package model.command;

import model.actors.BankAccount.AccountStatus;
import model.actors.PersonalAccount;
import model.capitalTransfer.Bill;
import model.capitalTransfer.Bill.BillStatus;
import model.capitalTransfer.Transaction;
import model.capitalTransfer.TransactionBuilder;
import model.system_data.dao.TransactionDAO;

public class PayBillCommand implements Command {

    private final PersonalAccount account; // λογαριασμός που θα πληρώσει
    private final Bill bill;
    private final double fee;
    private final TransactionDAO txDAO;

    public PayBillCommand(PersonalAccount account, Bill bill,double fee, TransactionDAO txDAO) {
        this.account = account;
        this.bill = bill;
        this.fee = fee;
        this.txDAO = txDAO;
    }

    @Override
    public CommandResult execute() {
    	if(account.getStatus().equals(AccountStatus.ACTIVE)) {
    		if(!bill.getStatus().equals(BillStatus.PENDING)) {
        		return new CommandResult(false, "Error paying bill : Bill is not Pending");
        	}
    		if(bill.getIssuer()==null) {
    			return new CommandResult(false, "Error paying bill : Issuer not found");
    		}
    		if(bill.getIssuer().getAccount().getStatus().equals(AccountStatus.BLOCKED)) {
    			return new CommandResult(false, "Error paying bill : Issuer is blocked");
    		}
        	
            try {

                //  Πληρωμή
                account.withdraw(bill.getAmount()+fee);
                bill.getIssuer().getAccount().addToBalance(bill.getAmount());
                
            } catch (RuntimeException e) {
                return new CommandResult(false, "Error paying bill: " + e.getMessage());
            }
            bill.markAsPaid(); // ενημερώνουμε το bill

            // Δημιουργία Transaction
            Transaction tx = new TransactionBuilder()
                    .from(account)
                    .to(bill.getIssuer().getFullName()) // where the money goes
                    .amount(bill.getAmount())
                    .type(Transaction.TransactionType.BILL_PAYMENT)
                    .build();

            // Αποθήκευση Transaction
            txDAO.save(tx);
            bill.getIssuer().getAccount().addTransaction(tx);

            return new CommandResult(true, "Bill paid successfully: " + bill.getBillId());
    	} else return new CommandResult(false, "Error paying bill: Account blocked");
    	
    	

        
    }
}

