package model.system_data.dao;

import java.util.List;
import java.util.stream.Collectors;
import model.capitalTransfer.Bill;
import model.system_data.persistence.FileDataStore;
import model.actors.BusinessClient;
import model.actors.PersonalClient;

public class FileBillDAO implements BillDAO {

    private final List<Bill> bills;

    public FileBillDAO() {
        this.bills = FileDataStore.getInstance().getBills();
    }

    @Override
    public List<Bill> findAll() {
        return List.copyOf(bills);
    }

    @Override
    public List<Bill> findByIssuer(BusinessClient issuer) {
        return bills.stream()
                    .filter(b -> b.getIssuer().equals(issuer))
                    .collect(Collectors.toList());
    }

    @Override
    public List<Bill> findByPayer(PersonalClient payer) {
        return bills.stream()
                    .filter(b -> b.getPayer().equals(payer))
                    .collect(Collectors.toList());
    }

    @Override
    public Bill findByBillCode(String billCode) {
        return bills.stream()
                    .filter(b -> b.getBillCode().equals(billCode))
                    .findFirst()
                    .orElse(null);
    }
    
    @Override
    public Bill findByBillId(String billId) {
        return bills.stream()
                    .filter(b -> b.getBillId().equals(billId))
                    .findFirst()
                    .orElse(null);
    }

    @Override
    public void save(Bill bill) {

        // 1. master list
        bills.add(bill);

        // 2. issuer & payer references
        bill.getIssuer().addBill(bill);
        bill.getPayer().addBill(bill);
    }

	@Override
	public void delete(Bill bill) {

	    bills.remove(bill);

	    bill.getIssuer().removeBill(bill);
	    bill.getPayer().removeBill(bill); 
	}
	
	@Override
	public void deleteByCode(String billCode) {
	    Bill bill = findByBillCode(billCode);
	    if (bill == null) return;

	    bills.remove(bill);
	    bill.getIssuer().removeBill(bill);
	    bill.getPayer().removeBill(bill);
	}



}



