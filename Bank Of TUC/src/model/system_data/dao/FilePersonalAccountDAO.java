package model.system_data.dao;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import model.actors.PersonalAccount;
import model.actors.PersonalClient;
import model.system_data.persistence.FileDataStore;


public class FilePersonalAccountDAO implements PersonalAccountDAO {

    private final List<PersonalAccount> accounts;

    public FilePersonalAccountDAO() {
        this.accounts = FileDataStore.getInstance().getPersonalAccounts();
    }

    @Override
    public List<PersonalAccount> findAll() {
        return List.copyOf(accounts);
    }
    
    @Override
    public PersonalAccount findByIban(String iban) {
        for (PersonalAccount a : accounts) {
            if (a.getIban().equals(iban)) {
                return a;
            }
        }
        return null;
    }

    @Override
    public List<PersonalAccount> findByClient(PersonalClient client) {
        return accounts.stream()
                .filter(acc ->
                        acc.getOwner().equals(client) ||
                        acc.getCoOwners().contains(client)
                )
                .collect(Collectors.toList());
    }


    @Override
    public void add(PersonalAccount account) {
        // 1. Προσθήκη στη master list
        accounts.add(account);

        // 2. Προσθήκη στον owner
        account.getOwner().addPersonalAccount(account);

        // 3. Προσθήκη σε co-owners (αν υπάρχουν)
        for (PersonalClient coOwner : account.getCoOwners()) {
            coOwner.addPersonalAccount(account);
        }
    }
    
    @Override
    public void delete(PersonalAccount account) {
        // 1. Αφαίρεση από master list
        accounts.remove(account);

        // 2. Αφαίρεση από owner
        account.getOwner().removePersonalAccount(account);

        // 3. Αφαίρεση από co-owners
        for (PersonalClient coOwner : account.getCoOwners()) {
            coOwner.removePersonalAccount(account);
        }
    }

}

