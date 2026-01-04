package model.system;

import model.actors.BankAccount;

public class PersonalInterestPolicy implements InterestPolicy {

    @Override
    public float calculateInterest(BankAccount account) {
        double balance = account.getBalance();

        if (balance < 1_000) {
            return 0.5f;
        } else if (balance < 10_000) {
            return 1.2f;
        } else {
            return 2.0f;
        }
    }
}

