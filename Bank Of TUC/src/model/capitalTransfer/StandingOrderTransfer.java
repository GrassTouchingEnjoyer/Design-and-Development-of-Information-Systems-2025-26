package model.capitalTransfer;

import java.time.LocalDate;

import model.actors.PersonalAccount;
import model.actors.PersonalClient;
import model.command.CommandResult;
import model.transfer.InternalTransferExecution;
import model.transfer.TransferExecution;

public abstract class StandingOrderTransfer extends StandingOrder{
	
	private int frequency;
	private int exeDate;
	private String reciever;
	private LocalDate nextExecutionDate;
	

	public StandingOrderTransfer(String description, String name, PersonalClient owner, double amount,
			PersonalAccount account, LocalDate issueDate, LocalDate dueDate, double fees, int frequency,
			int exeDate ,String reciever) {
		super(description, name, owner, amount, account, issueDate, dueDate,fees);
		// TODO Auto-generated constructor stub
		super.type = StandingOrderType.TRANSFER;
		this.frequency=frequency;
		this.exeDate=exeDate;
		this.reciever=reciever;
		
		if(issueDate.getDayOfMonth()<exeDate) {
			this.nextExecutionDate =LocalDate.of(issueDate.getYear(),
					issueDate.getMonthValue(), exeDate);
		}else {
			this.nextExecutionDate =LocalDate.of(issueDate.getYear(),
					issueDate.getMonthValue()+1, exeDate);
		}
	}
	
	public void updateNextExecutionDate(LocalDate executionDate) {
		this.nextExecutionDate = executionDate.plusMonths(frequency);
	}


	public int getFrequency() {
		return frequency;
	}

	public void setFrequency(int frequency) {
		this.frequency = frequency;
	}

	public int getExeDate() {
		return exeDate;
	}

	public void setExeDate(int exeDate) {
		this.exeDate = exeDate;
	}

	public String getReciever() {
		return reciever;
	}

	public void setReciever(String reciever) {
		this.reciever = reciever;
	}


	public LocalDate getNextExecutionDate() {
		return nextExecutionDate;
	}

	public void setNextExecutionDate(LocalDate nextExecutionDate) {
		this.nextExecutionDate = nextExecutionDate;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();

		sb.append(name)
		  .append("  ")
		  .append(amount)
		  .append(" € ")
		  .append(type)
		  .append("  to : ")
		  .append(reciever)
		  .append("  Until : ")
		  .append(dueDate)
		  .append(" Every ")
		  .append(frequency)
		  .append(" months");

		String result = sb.toString();

		return result;
	}


	@Override
	public boolean isExecutable() {
		if(LocalDate.now().isEqual(nextExecutionDate))
		return true;
		else return false;
	}


	@Override
	public void execute() {
		updateNextExecutionDate(LocalDate.now());
		
	}
	
	
}
