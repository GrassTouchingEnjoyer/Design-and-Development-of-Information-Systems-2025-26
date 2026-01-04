package model.system_data.dao;

import java.util.List;

import model.actors.PersonalClient;
import model.system_data.persistence.FileDataStore;


public class FilePersonalClientDAO implements PersonalClientDAO {

    private final List<PersonalClient> clients;

    public FilePersonalClientDAO() {
        this.clients = FileDataStore.getInstance().getPersonalClients();
    }

    @Override
    public List<PersonalClient> findAll() {
        return List.copyOf(clients); // προστασία
    }

    @Override
    public PersonalClient findByFullName(String fullName) {
        return clients.stream()
                .filter(c -> c.getFullName().equals(fullName))
                .findFirst()
                .orElse(null);
    }
    
    @Override
    public PersonalClient findByUsername(String username) {
        return clients.stream()
                .filter(c -> c.getUsername().equals(username))
                .findFirst()
                .orElse(null);
    }

    @Override
    public void save(PersonalClient client) {
        clients.add(client);
    }
    
    @Override
    public void delete(PersonalClient client) {
        // Διαγραφή accounts
        client.getAccounts().forEach(acc -> {
            FileDataStore.getInstance().getPersonalAccounts().remove(acc);
        });

        // Διαγραφή client
        clients.remove(client);
    }
}
