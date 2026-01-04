package model.capitalTransfer;

import java.time.LocalDate;

import model.actors.PersonalAccount;
import model.actors.PersonalClient;

public class StandingOrderTransferSEPA extends StandingOrderTransfer{
	private String bic;

	public StandingOrderTransferSEPA(String description, String name, PersonalClient owner, double amount,
			PersonalAccount account, LocalDate issueDate, LocalDate dueDate, double fees, int frequency, int exeDate,
			String reciever,String bic) {
		super(description, name, owner, amount, account, issueDate, dueDate, fees, frequency, exeDate, reciever);
		// TODO Auto-generated constructor stub
		this.bic = bic;
	}

	public String getBic() {
		return bic;
	}

	public void setBic(String bic) {
		this.bic = bic;
	}

}
