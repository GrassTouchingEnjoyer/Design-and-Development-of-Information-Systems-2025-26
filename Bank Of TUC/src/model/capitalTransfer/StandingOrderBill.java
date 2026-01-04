package model.capitalTransfer;

import java.time.LocalDate;

import model.actors.PersonalAccount;
import model.actors.PersonalClient;
import model.capitalTransfer.Bill.BillStatus;
import model.command.CommandResult;

public class StandingOrderBill extends StandingOrder{
	
	private String billCode;

	public StandingOrderBill(String description, String name, PersonalClient owner, double amount,
			PersonalAccount account, LocalDate issueDate, LocalDate dueDate, double fees,String billCode) {
		super(description, name, owner, amount, account, issueDate, dueDate, fees);
		super.type = StandingOrderType.BILL_PAYMENT;
		// TODO Auto-generated constructor stub
		this.billCode=billCode;
	}

	public String getBillCode() {
		return billCode;
	}

	public void setBillCode(String billCode) {
		this.billCode = billCode;
	}
	
	public Bill getBill() {
		for (Bill b : owner.getBills()) {
		    if (b.getBillCode().equals(billCode) && b.getStatus().equals(BillStatus.PENDING)) {
		        return b;
		    }
		}
		return null;
	}

	@Override
	public boolean isExecutable() {
		for (Bill b : owner.getBills()) {
		    if (b.getBillCode().equals(billCode) && b.getStatus().equals(BillStatus.PENDING)) {
		        return true;
		    }
		}

		return false;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();

		sb.append(name)
		  .append("  ")
		  .append(amount)
		  .append(" € ")
		  .append(type)
		  .append("  Bill Code : ")
		  .append(billCode)
		  .append("  Until : ")
		  .append(dueDate);

		String result = sb.toString();
		
		return result;
	}

	@Override
	public void execute() {
		// TODO Auto-generated method stub
		
	}
	
}
