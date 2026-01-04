package model.builder;

import java.time.LocalDate;

import model.actors.PersonalAccount;
import model.actors.PersonalClient;
import model.capitalTransfer.StandingOrderTransfer;

//Builder interface
public interface StandingOrderTransferBuilder<T extends StandingOrderTransfer> {
 StandingOrderTransferBuilder<T> description(String description);
 StandingOrderTransferBuilder<T> name(String name);
 StandingOrderTransferBuilder<T> owner(PersonalClient owner);
 StandingOrderTransferBuilder<T> amount(double amount);
 StandingOrderTransferBuilder<T> account(PersonalAccount account);
 StandingOrderTransferBuilder<T> issueDate(LocalDate issueDate);
 StandingOrderTransferBuilder<T> dueDate(LocalDate dueDate);
 StandingOrderTransferBuilder<T> fees(double fees);
 StandingOrderTransferBuilder<T> frequency(int frequency);
 StandingOrderTransferBuilder<T> exeDate(int exeDate);
 StandingOrderTransferBuilder<T> reciever(String reciever);

 T build();
}

