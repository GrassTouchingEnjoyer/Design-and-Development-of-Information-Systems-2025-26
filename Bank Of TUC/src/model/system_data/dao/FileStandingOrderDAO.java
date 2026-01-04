package model.system_data.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import model.actors.PersonalAccount;
import model.actors.PersonalClient;
import model.capitalTransfer.StandingOrder;
import model.system_data.persistence.FileDataStore;


public class FileStandingOrderDAO implements StandingOrderDAO {

    private final List<StandingOrder> standingOrders = new ArrayList<>();
    private final FileDataStore store;

    public FileStandingOrderDAO() {
        // Παίρνουμε όλα τα accounts και φορτώνουμε τα standing orders τους
        store = FileDataStore.getInstance();

        store.getPersonalAccounts().forEach(acc -> {
            standingOrders.addAll(acc.getStandingOrders());
        });
    }

    @Override
    public List<StandingOrder> findAll() {
        return List.copyOf(standingOrders);
    }

    @Override
    public List<StandingOrder> findByOwner(PersonalClient owner) {
        return standingOrders.stream()
                .filter(so -> so.getOwner().equals(owner))
                .collect(Collectors.toList());
    }

    @Override
    public List<StandingOrder> findByAccount(PersonalAccount account) {
        return standingOrders.stream()
                .filter(so -> so.getAccount().equals(account))
                .collect(Collectors.toList());
    }
    
    @Override
    public void save(StandingOrder order) {

        PersonalAccount account = order.getAccount();

        account.addStandingOrder(order);
    }
    
    @Override
    public void delete(StandingOrder order) {
        order.getAccount().removeStandingOrder(order);
    }
    
    @Override
    public void deleteByName(String orderName) {
        StandingOrder so = findByName(orderName);
        if (so == null) return;

        so.getAccount().removeStandingOrder(so);
    }
    
    @Override
    public StandingOrder findByName(String name) {

        for (PersonalAccount acc : store.getPersonalAccounts()) {

            for (StandingOrder so : acc.getStandingOrders()) {

                if (so.getName().equals(name)) {
                    return so;
                }
            }
        }
        return null;
    }

}

