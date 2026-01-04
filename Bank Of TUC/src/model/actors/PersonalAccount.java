package model.actors;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.UUID;

import model.capitalTransfer.StandingOrder;
import model.capitalTransfer.StandingOrderBill;
import model.capitalTransfer.StandingOrderTransfer;
import model.capitalTransfer.Transaction;
import model.capitalTransfer.Transaction.TransactionType;
import model.capitalTransfer.TransactionBuilder;

public class PersonalAccount extends BankAccount {

    private ArrayList<StandingOrder> standingOrders = new ArrayList<StandingOrder>();
    private ArrayList<PersonalClient> coOwners= new ArrayList<PersonalClient>(); 
    private PersonalClient owner;

    public PersonalAccount(String iban, PersonalClient owner, double balance) {
        super(iban, balance);
        this.standingOrders = new ArrayList<>();
        this.coOwners = new ArrayList<>();
        this.owner =owner;
    }

    public ArrayList<PersonalClient> getCoOwners() {
        return coOwners;
    }

    public void addCoOwner(PersonalClient client) {
    	if(client!=null) {
    		if (!coOwners.contains(client)) {
                coOwners.add(client);
            }
    	}
    }

    public void addStandingOrder(StandingOrder so) {
        standingOrders.add(so);
    }
    
    public void removeStandingOrder(StandingOrder so) {
    	standingOrders.remove(so);
    }

    public ArrayList<StandingOrder> getStandingOrders() {
		return standingOrders;
	}
    
    public void deposit(double amount) {

	    if (amount <= 0) {
	        throw new IllegalArgumentException("Amount must be positive");
	    }

	    balance += amount;

	}
	
	public void withdraw(double amount) {

	    if (amount <= 0) {
	        throw new IllegalArgumentException("Amount must be positive");
	    }

	    if (balance < amount) {
	        throw new IllegalStateException("Insufficient funds");
	    }

	    balance -= amount;

	}

	public PersonalClient getOwner() {
		return owner;
	}

	public void setOwner(PersonalClient owner) {
		this.owner = owner;
	}

    @Override
    public String toString() {
        return iban;
    }

    
}