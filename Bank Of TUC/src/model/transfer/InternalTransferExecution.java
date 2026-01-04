package model.transfer;

import model.actors.BankAccount;
import model.actors.PersonalAccount;
import model.capitalTransfer.Transaction;
import model.system_data.dao.BusinessClientDAO;
import model.system_data.dao.PersonalAccountDAO;

public class InternalTransferExecution implements TransferExecution {

    private final PersonalAccountDAO personalAccountDAO;
    private final BusinessClientDAO businessClientDAO;
    protected final double fee;

    public InternalTransferExecution(
            PersonalAccountDAO pDao,
            BusinessClientDAO bDao,double fee) {
        this.personalAccountDAO = pDao;
        this.businessClientDAO = bDao;
        this.fee = fee;
    }

    @Override
    public TransferResult execute(
            PersonalAccount from,
            String toIban,
            double amount) {

        BankAccount to =
            personalAccountDAO.findByIban(toIban);

        if (to == null) {
            to = businessClientDAO.findByIban(toIban);
        }

        if (to == null) {
            return TransferResult.failure("Target account not found");
        }

        if (from.getBalance() < amount + fee) {
        	return TransferResult.failure("Insufficient funds");
        }

        from.setBalance(from.getBalance() - amount - fee);
        to.setBalance(to.getBalance() + amount);

        return TransferResult.success("Internal transfer completed successfully");
    }
    
    @Override
    public Transaction.TransactionType getTransactionType() {
        return Transaction.TransactionType.INTERNAL_TRANSFER;
    }
}

