package model.factory;

import model.actors.BankAccount;
import model.actors.BusinessAccount;
import model.actors.BusinessClient;
import model.actors.Client;
import model.actors.PersonalAccount;
import model.actors.PersonalClient;

public class AccountFactory {

    public static BankAccount createAccount(String type, Client owner, String iban,
                                            double balance) {
        switch (type.toUpperCase()) {
            case "PERSONAL":
                return new PersonalAccount(iban, (PersonalClient) owner, balance);
            case "BUSINESS":
                return new BusinessAccount(iban, (BusinessClient) owner, balance);
            default:
                throw new IllegalArgumentException("Unknown account type: " + type);
        }
    }
}
