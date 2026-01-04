package model.system;

import model.actors.BankAccount;

public interface InterestPolicy {
	float calculateInterest(BankAccount account);
}

