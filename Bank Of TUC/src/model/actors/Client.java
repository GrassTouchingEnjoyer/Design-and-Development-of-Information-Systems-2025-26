package model.actors;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import model.capitalTransfer.Bill;
import model.capitalTransfer.Bill.BillStatus;

public abstract class Client extends User {
	
	protected ArrayList<Bill> bills = new ArrayList<>();
    
    public Client(String username, String password, String fullName, String afm, String phone) {
        super(username, password,fullName,afm,phone);
        
    }
    
    public void addBill(Bill bill){
		this.bills.add(bill);
	}
    
    public void removeBill(Bill bill){
		bills.remove(bill);
	}

	public ArrayList<Bill> getBills() {
		return bills;
	}
	
	public List<Bill> getBillsByStatus(BillStatus status) {
        return bills.stream()
                    .filter(b -> b.getStatus() == status)
                    .collect(Collectors.toList());
    }

}
