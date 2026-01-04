package model.command;

import model.actors.BankAccount.AccountStatus;
import model.capitalTransfer.StandingOrder;
import model.capitalTransfer.StandingOrderBill;
import model.capitalTransfer.StandingOrderTransfer;
import model.capitalTransfer.StandingOrderTransferInternal;
import model.capitalTransfer.StandingOrderTransferSEPA;
import model.capitalTransfer.StandingOrderTransferSWIFT;
import model.system_data.dao.BusinessClientDAO;
import model.system_data.dao.PersonalAccountDAO;
import model.system_data.dao.TransactionDAO;
import model.transfer.InternalTransferExecution;
import model.transfer.SEPATransferExecution;
import model.transfer.SWIFTTransferExecution;
import model.transfer.TransferExecution;

public class StandingOrderCommand implements Command {

    private final StandingOrder order;
    private final TransactionDAO txDAO;
    private final PersonalAccountDAO pAccDAO;
    private final BusinessClientDAO bClientDAO;

    public StandingOrderCommand(
        StandingOrder order,
        TransactionDAO txDAO,
        PersonalAccountDAO pAccDAO,
        BusinessClientDAO bClientDAO
    ) {
        this.order = order;
        this.txDAO = txDAO;
        this.pAccDAO = pAccDAO;
        this.bClientDAO = bClientDAO;
    }

    @Override
    public CommandResult execute() {
    	
    		if (order.isDue()) {
                return new CommandResult(false, "StandingOrder is due");
            }
        	
            if (!order.isExecutable()) {
                return new CommandResult(false, "false");
            }
            
         if(order.getAccount().getStatus().equals(AccountStatus.ACTIVE)) {
            Command cmd;

            if (order instanceof StandingOrderBill bill) {
            	
            	if(bill.getAmount()>=bill.getBill().getAmount()) {
            		cmd = new PayBillCommand(
                            bill.getAccount(),
                            bill.getBill(),
                            bill.getFees(),
                            txDAO
                        );
            	}else {
            		return new CommandResult(false, "Bill costs more than the specified amount");
            	}
            } else if (order instanceof StandingOrderTransfer transfer) {
            	TransferExecution method;
            	if(transfer instanceof StandingOrderTransferInternal internalTransfer) {
            		method= new InternalTransferExecution(pAccDAO, bClientDAO,internalTransfer.getFees());
            	}else if(transfer instanceof StandingOrderTransferSEPA sepaTransfer) {
            		method = new SEPATransferExecution(sepaTransfer.getFees(), sepaTransfer.getBic());
            	}else if(transfer instanceof StandingOrderTransferSWIFT swiftTransfer) {
            		method = new SWIFTTransferExecution(swiftTransfer.getFees(), swiftTransfer.getSwiftCode());
            	}else {
            		return new CommandResult(false, "Unknown transfer type");
            	}
            	
                cmd = new TransferCommand(
                    transfer.getAccount(),
                    transfer.getReciever(),
                    transfer.getAmount(),
                    method,
                    txDAO,
                    pAccDAO,
                    bClientDAO
                );
            } else {
            	return new CommandResult(false, "Unknown StandingOrder type");
            }

            CommandResult result = cmd.execute();
            if(result.isSuccess()) {
            	order.execute();
            }

            return result;
    	}else return new CommandResult(false, "StandingOrder failed : Account is blocked");
    	
    }
}
