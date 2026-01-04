package model.capitalTransfer;

import java.io.Serializable;
import java.time.*;
import java.time.format.DateTimeFormatter;

import model.actors.BankAccount;

public class Transaction implements Serializable{
	private String transactionId;
	private BankAccount fromAccount;
	private String toAccount;
	private double amount;
	private LocalDateTime timeOfExecution;
	private TransactionType type;
	
	public Transaction(String transactionId,BankAccount fromAccount,
			String toAccount,double amount,LocalDateTime timeOfExecution) {
		this.amount=amount;
		this.fromAccount=fromAccount;
		this.timeOfExecution=timeOfExecution;
		this.toAccount=toAccount;
		this.transactionId=transactionId;
	}
	
	@Override
	public String toString() {
	    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

	    StringBuilder sb = new StringBuilder();
	    
	    sb.append(type != null ? type : "N/A");
	    sb.append(String.format(" %.2f €", amount));
	    if(toAccount != null) {
	    	sb.append("  To: ")
		      .append(toAccount);
	    }
	    sb.append(" \n At: ")
	      .append(timeOfExecution != null ? timeOfExecution.format(formatter) : "N/A");

	    return sb.toString();
	}
	
	
	
	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public BankAccount getFromAccount() {
		return fromAccount;
	}

	public void setFromAccount(BankAccount fromAccount) {
		this.fromAccount = fromAccount;
	}

	public String getToAccount() {
		return toAccount;
	}

	public void setToAccount(String toAccount) {
		this.toAccount = toAccount;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public LocalDateTime getTimeOfExecution() {
		return timeOfExecution;
	}

	public void setTimeOfExecution(LocalDateTime timeOfExecution) {
		this.timeOfExecution = timeOfExecution;
	}

	public TransactionType getType() {
		return type;
	}

	public void setType(TransactionType type) {
		this.type = type;
	}


	public enum TransactionType{
		DEPOSIT,
		WITHDRAWAL,
		INTERNAL_TRANSFER,
		EXTERNAL_TRANSFER,
		BILL_PAYMENT
	}
	
	
	
}
