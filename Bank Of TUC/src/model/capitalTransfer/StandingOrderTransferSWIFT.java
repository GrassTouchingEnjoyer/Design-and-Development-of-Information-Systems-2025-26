package model.capitalTransfer;

import java.time.LocalDate;

import model.actors.PersonalAccount;
import model.actors.PersonalClient;

public class StandingOrderTransferSWIFT extends StandingOrderTransfer{
	private String swiftCode;

	public StandingOrderTransferSWIFT(String description, String name, PersonalClient owner, double amount,
			PersonalAccount account, LocalDate issueDate, LocalDate dueDate, double fees, int frequency, int exeDate,
			String reciever,String swiftCode) {
		super(description, name, owner, amount, account, issueDate, dueDate, fees, frequency, exeDate, reciever);
		// TODO Auto-generated constructor stub
		this.swiftCode = swiftCode;
	}

	public String getSwiftCode() {
		return swiftCode;
	}

	public void setSwiftCode(String swiftCode) {
		this.swiftCode = swiftCode;
	}

}
