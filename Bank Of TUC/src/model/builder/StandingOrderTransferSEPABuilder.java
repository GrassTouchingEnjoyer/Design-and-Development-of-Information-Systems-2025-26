package model.builder;

import java.time.LocalDate;

import model.actors.PersonalAccount;
import model.actors.PersonalClient;
import model.capitalTransfer.StandingOrderTransferSEPA;

public class StandingOrderTransferSEPABuilder implements StandingOrderTransferBuilder<StandingOrderTransferSEPA> {
    private String description;
    private String name;
    private PersonalClient owner;
    private double amount;
    private PersonalAccount account;
    private LocalDate issueDate;
    private LocalDate dueDate;
    private double fees;
    private int frequency;
    private int exeDate;
    private String reciever;
    private String bic;

    public StandingOrderTransferSEPABuilder bic(String bic) {
        this.bic = bic;
        return this;
    }

    @Override
    public StandingOrderTransferSEPABuilder description(String description) { this.description = description; return this; }
    @Override
    public StandingOrderTransferSEPABuilder name(String name) { this.name = name; return this; }
    @Override
    public StandingOrderTransferSEPABuilder owner(PersonalClient owner) { this.owner = owner; return this; }
    @Override
    public StandingOrderTransferSEPABuilder amount(double amount) { this.amount = amount; return this; }
    @Override
    public StandingOrderTransferSEPABuilder account(PersonalAccount account) { this.account = account; return this; }
    @Override
    public StandingOrderTransferSEPABuilder issueDate(LocalDate issueDate) { this.issueDate = issueDate; return this; }
    @Override
    public StandingOrderTransferSEPABuilder dueDate(LocalDate dueDate) { this.dueDate = dueDate; return this; }
    @Override
    public StandingOrderTransferSEPABuilder fees(double fees) { this.fees = fees; return this; }
    @Override
    public StandingOrderTransferSEPABuilder frequency(int frequency) { this.frequency = frequency; return this; }
    @Override
    public StandingOrderTransferSEPABuilder exeDate(int exeDate) { this.exeDate = exeDate; return this; }
    @Override
    public StandingOrderTransferSEPABuilder reciever(String reciever) { this.reciever = reciever; return this; }

    @Override
    public StandingOrderTransferSEPA build() {
        return new StandingOrderTransferSEPA(description, name, owner, amount, account, issueDate, dueDate, fees, frequency, exeDate, reciever, bic);
    }
}

