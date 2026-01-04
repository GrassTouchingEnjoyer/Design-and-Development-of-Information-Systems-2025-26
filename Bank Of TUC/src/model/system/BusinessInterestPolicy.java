package model.system;

import model.actors.BankAccount;

public class BusinessInterestPolicy implements InterestPolicy {

    @Override
    public float calculateInterest(BankAccount account) {
        double balance = account.getBalance();

        if (balance < 50_000) {
            return 0.3f;
        } else {
            return 0.8f;
        }
    }
}

