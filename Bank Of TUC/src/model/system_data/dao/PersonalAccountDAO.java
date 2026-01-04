package model.system_data.dao;

import java.util.List;
import java.util.Optional;

import model.actors.PersonalAccount;
import model.actors.PersonalClient;

public interface PersonalAccountDAO {

    List<PersonalAccount> findAll();

    PersonalAccount findByIban(String iban);

    List<PersonalAccount> findByClient(PersonalClient client);

	void add(PersonalAccount account);

	void delete(PersonalAccount account);
}
