package model.system_data.dao;
import java.util.List;
import java.util.Optional;

import model.actors.BusinessAccount;
import model.actors.BusinessClient;

public interface BusinessClientDAO {

    List<BusinessClient> findAll();

    BusinessClient findByFullName(String fullName);

	void delete(BusinessClient client);

	void add(BusinessClient client);

	List<BusinessAccount> getAllBusinessAccounts();

	BusinessAccount findByIban(String iban);

	BusinessClient findByUsername(String username);
}

