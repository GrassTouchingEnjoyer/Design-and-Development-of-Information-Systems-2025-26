package model.builder;

import java.time.LocalDate;

import model.actors.PersonalAccount;
import model.actors.PersonalClient;
import model.capitalTransfer.StandingOrderTransferSWIFT;

public class StandingOrderTransferSWIFTBuilder implements StandingOrderTransferBuilder<StandingOrderTransferSWIFT> {
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
    private String swiftCode;

    public StandingOrderTransferSWIFTBuilder swiftCode(String swiftCode) {
        this.swiftCode = swiftCode;
        return this;
    }

    @Override
    public StandingOrderTransferSWIFTBuilder description(String description) { this.description = description; return this; }
    @Override
    public StandingOrderTransferSWIFTBuilder name(String name) { this.name = name; return this; }
    @Override
    public StandingOrderTransferSWIFTBuilder owner(PersonalClient owner) { this.owner = owner; return this; }
    @Override
    public StandingOrderTransferSWIFTBuilder amount(double amount) { this.amount = amount; return this; }
    @Override
    public StandingOrderTransferSWIFTBuilder account(PersonalAccount account) { this.account = account; return this; }
    @Override
    public StandingOrderTransferSWIFTBuilder issueDate(LocalDate issueDate) { this.issueDate = issueDate; return this; }
    @Override
    public StandingOrderTransferSWIFTBuilder dueDate(LocalDate dueDate) { this.dueDate = dueDate; return this; }
    @Override
    public StandingOrderTransferSWIFTBuilder fees(double fees) { this.fees = fees; return this; }
    @Override
    public StandingOrderTransferSWIFTBuilder frequency(int frequency) { this.frequency = frequency; return this; }
    @Override
    public StandingOrderTransferSWIFTBuilder exeDate(int exeDate) { this.exeDate = exeDate; return this; }
    @Override
    public StandingOrderTransferSWIFTBuilder reciever(String reciever) { this.reciever = reciever; return this; }

    @Override
    public StandingOrderTransferSWIFT build() {
        return new StandingOrderTransferSWIFT(description, name, owner, amount, account, issueDate, dueDate, fees, frequency, exeDate, reciever, swiftCode);
    }
}

