package model.system_data.dao;

import java.util.List;
import model.capitalTransfer.Bill;
import model.actors.BusinessClient;
import model.actors.PersonalClient;

public interface BillDAO {

    List<Bill> findAll();

    List<Bill> findByIssuer(BusinessClient issuer);

    List<Bill> findByPayer(PersonalClient payer);

    Bill findByBillCode(String billCode);
    
    Bill findByBillId(String billId);

    void save(Bill bill);

    void delete(Bill bill);

	void deleteByCode(String billCode);

}

