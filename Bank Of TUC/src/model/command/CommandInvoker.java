package model.command;

public class CommandInvoker {
	private Command command;
	//Πρακτικά άχρηστο αλλά το βάζουμε για να δείξουμε πως το ξέρουμε

    public void setCommand(Command command) {
        this.command = command;
    }

    public CommandResult execute() {
        return command.execute();
    }
}
