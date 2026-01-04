package model.actors;

import java.io.Serializable;
import java.time.Clock;
import java.time.YearMonth;
import java.util.ArrayList;

import model.capitalTransfer.Bill;
import model.capitalTransfer.Transaction;

public abstract class BankAccount implements Serializable{
	
	protected final String iban;
	protected float interestRate;
	protected double balance;
	protected AccountStatus status;
	protected ArrayList<Transaction> transactions = new ArrayList<Transaction>();
	protected YearMonth lastInterestApplied;
	
	public BankAccount(String iban,double balance) {
		this.iban=iban;
		this.interestRate=0f;
		this.balance=balance;
		this.status=AccountStatus.ACTIVE;
	}
	
	
	public void applyInterest() {
		YearMonth currentMonth = YearMonth.now();

		if (!currentMonth.equals(lastInterestApplied)) {
			double interest = balance * (interestRate / 100);
		    balance += interest;
		    lastInterestApplied=currentMonth;
		}
	}
	
	
	public void block() {
		status=AccountStatus.BLOCKED;
	}
	
	public void unblock() {
		status=AccountStatus.ACTIVE;
	}
	
	public void addTransaction(Transaction transaction) {
		transactions.add(transaction);
	}
	
	public void removeTransaction(Transaction transaction) {
		transactions.remove(transaction);
	}
	
	public ArrayList<Transaction> getTransactions() {
		return transactions;
	}


	public float getInterestRate() {
		return interestRate;
	}

	public void setInterestRate(float interestRate) {
		this.interestRate = interestRate;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}
	
	public void addToBalance(double amount) {
		this.balance+=amount;
	}

	public AccountStatus getStatus() {
		return status;
	}

	public String getIban() {
		return iban;
	}


	public void setStatus(AccountStatus status) {
		this.status = status;
	}

	public enum AccountStatus {
		  ACTIVE,
		  BLOCKED
		}

}
