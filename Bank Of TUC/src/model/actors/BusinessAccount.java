package model.actors;

import java.time.YearMonth;
import java.util.ArrayList;

import model.capitalTransfer.*;

public class BusinessAccount extends BankAccount{
	
	private double monthlyUpkeepFee = 0; 
	private BusinessClient owner;
	private YearMonth lastFeeApplied;
	
	public BusinessAccount(String iban, BusinessClient owner, double balance) {
		
		super(iban, balance);		
		this.monthlyUpkeepFee = 0;
		this.owner=owner;
		
	}
	
	public double getMonthlyUpkeepFee() {
		return monthlyUpkeepFee;
	}

	public void setmonthlyUpkeepFee(double monthlyUpkeepFee) {
		this.monthlyUpkeepFee = monthlyUpkeepFee;
	}
	
	public BusinessClient getOwner() {
		return owner;
	}

	public void setOwner(BusinessClient owner) {
		this.owner = owner;
	}
	
	public void applyFee() {
		YearMonth currentMonth = YearMonth.now();

		if (!currentMonth.equals(lastFeeApplied)) {
			if(this.balance>=monthlyUpkeepFee) {
				this.balance-=monthlyUpkeepFee;
			}
		    lastFeeApplied=currentMonth;
		}
	}
	

	@Override
	public String toString() {
		return  iban + "\nowner=" + owner
				;
	}


	
	
}
