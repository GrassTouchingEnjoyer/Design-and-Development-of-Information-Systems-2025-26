package model.system_data.dao;

import java.util.List;

import model.actors.PersonalAccount;
import model.actors.PersonalClient;
import model.capitalTransfer.StandingOrder;

public interface StandingOrderDAO {
    List<StandingOrder> findAll();
    List<StandingOrder> findByOwner(PersonalClient owner);
    List<StandingOrder> findByAccount(PersonalAccount account);
	void save(StandingOrder order);
	void delete(StandingOrder order);
	void deleteByName(String orderId);
	StandingOrder findByName(String id);
}
