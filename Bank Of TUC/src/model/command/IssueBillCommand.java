package model.command;

import java.time.LocalDate;

import model.actors.BankAccount.AccountStatus;
import model.actors.BusinessClient;
import model.actors.PersonalClient;
import model.capitalTransfer.Bill;
import model.system_data.dao.BillDAO;

public class IssueBillCommand implements Command {

	private BusinessClient issuer;
	private final String billId;
	private final String billCode;
	private PersonalClient payer; 
	private final double amount;
	private final LocalDate issueDate;
	private final LocalDate dueDate;
	private final String description;
	private final BillDAO bdao;

    public IssueBillCommand(BusinessClient issuer,
    						String billId,
    						PersonalClient payer,
                            String billCode,
                            double amount,
                            LocalDate dueDate,
                            String description,
                            BillDAO bdao) {
    	this.issuer = issuer;
		this.billId = billId;
		this.billCode = billCode;
		this.payer = payer;
		this.amount = amount;
		this.issueDate = LocalDate.now();
		this.dueDate = dueDate;
		this.description = description;
		this.bdao = bdao;
    }

    @Override
    public CommandResult execute() {
    	if(issuer.getAccount().getStatus().equals(AccountStatus.ACTIVE)) {
    		try {
                // Δημιουργία Bill
                Bill bill = new Bill(
                    issuer,
                    billId,
                    billCode,
                    payer,
                    amount,
                    issueDate,
                    dueDate,
                    description
                );

                // Προσθήκη στους λογαριασμούς
                bdao.save(bill);

                return new CommandResult(true, "Bill issued successfully: " + billCode);

            } catch (RuntimeException e) {
                return new CommandResult(false, "Error issuing bill: " + e.getMessage());
            }
    	}else return new CommandResult(false, "Error issuing bill: Account is blocked" );
        
    }
}

