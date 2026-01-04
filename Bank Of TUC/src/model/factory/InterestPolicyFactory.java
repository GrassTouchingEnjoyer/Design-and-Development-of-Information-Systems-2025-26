package model.factory;

import model.actors.BankAccount;
import model.actors.BusinessAccount;
import model.actors.PersonalAccount;
import model.system.BusinessInterestPolicy;
import model.system.InterestPolicy;
import model.system.PersonalInterestPolicy;

public class InterestPolicyFactory {

    public static InterestPolicy forAccount(BankAccount account) {
        if (account instanceof PersonalAccount) {
            return new PersonalInterestPolicy();
        }
        if (account instanceof BusinessAccount) {
            return new BusinessInterestPolicy();
        }
        throw new IllegalArgumentException("Unknown account type");
    }
}
