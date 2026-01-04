package model.capitalTransfer;

import java.io.Serializable;
import java.time.LocalDate;

import model.actors.*;
import model.command.Command;

public abstract class StandingOrder implements Serializable{
	
	protected String description;
	protected String name; //unique
	protected PersonalClient owner;
	protected double amount;
	protected PersonalAccount account;
	protected LocalDate issueDate;
	protected LocalDate dueDate;
	protected double fees;
	protected StandingOrderType type;
	
	
	public StandingOrder(String description, String name, PersonalClient owner, double amount,
			PersonalAccount account,LocalDate issueDate, LocalDate dueDate,double fees) {
		this.description = description;
		this.name = name;
		this.owner = owner;
		this.amount = amount;
		this.account = account;
		this.issueDate = issueDate;
		this.dueDate = dueDate;
		this.fees = fees;
	}
	
	public boolean isDue() {
        return !LocalDate.now().isBefore(dueDate);
    }
	
	public abstract boolean isExecutable();
	
	public abstract void execute();
	
	@Override
	public abstract String toString();
	
	public String getDescription() {
		return description;
	}
	public String getName() {
		return name;
	}
	public PersonalClient getOwner() {
		return owner;
	}
	public double getAmount() {
		return amount;
	}
	public PersonalAccount getAccount() {
		return account;
	}
	public LocalDate getIssueDate() {
		return issueDate;
	}
	public LocalDate getDueDate() {
		return dueDate;
	}

	public double getFees() {
		return fees;
	}
	public StandingOrderType getType() {
		return type;
	}
	
	public enum StandingOrderType{
		TRANSFER,
		BILL_PAYMENT
	}
	
	
}
