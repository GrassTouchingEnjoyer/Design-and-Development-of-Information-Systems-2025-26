package model.capitalTransfer;

import java.time.LocalDate;

import model.actors.PersonalAccount;
import model.actors.PersonalClient;

public class StandingOrderTransferInternal extends StandingOrderTransfer{

	public StandingOrderTransferInternal(String description, String name, PersonalClient owner, double amount,
			PersonalAccount account, LocalDate issueDate, LocalDate dueDate, double fees, int frequency, int exeDate,
			String reciever) {
		super(description, name, owner, amount, account, issueDate, dueDate, fees, frequency, exeDate, reciever);
		// TODO Auto-generated constructor stub
	}

}
