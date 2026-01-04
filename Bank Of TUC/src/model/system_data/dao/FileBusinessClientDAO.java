package model.system_data.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import model.actors.BusinessAccount;
import model.actors.BusinessClient;
import model.actors.PersonalAccount;
import model.system_data.persistence.FileDataStore;

public class FileBusinessClientDAO implements BusinessClientDAO {

    private final List<BusinessClient> clients;

    public FileBusinessClientDAO() {
        this.clients = FileDataStore.getInstance().getBusinessClients();
    }

    @Override
    public List<BusinessClient> findAll() {
        return List.copyOf(clients);
    }

    @Override
    public BusinessClient findByFullName(String fullName) {
        return clients.stream()
                .filter(c -> c.getFullName().equals(fullName))
                .findFirst()
                .orElse(null);
    }
    
    @Override
    public BusinessClient findByUsername(String username) {
        return clients.stream()
                .filter(c -> c.getUsername().equals(username))
                .findFirst()
                .orElse(null);
    }
    
    @Override
    public List<BusinessAccount> getAllBusinessAccounts() {
        List<BusinessAccount> accounts = new ArrayList<>();
        for (BusinessClient bc : clients) {
            if (bc.getAccount() != null) {
                accounts.add(bc.getAccount());
            }
        }
        return accounts;
    }
    
    @Override
    public BusinessAccount findByIban(String iban) {
        for (BusinessClient bc : clients) {
            BusinessAccount acc = bc.getAccount();
            if (acc != null && acc.getIban().equals(iban)) {
                return acc;
            }
        }
        return null;
    }

    @Override
    public void add(BusinessClient client) {
        clients.add(client);
    }
    
    @Override
    public void delete(BusinessClient client) {
        clients.remove(client);
    }
}

