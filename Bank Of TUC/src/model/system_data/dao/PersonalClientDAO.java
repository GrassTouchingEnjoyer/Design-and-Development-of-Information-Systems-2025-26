package model.system_data.dao;

import java.util.List;
import model.actors.PersonalClient;

public interface PersonalClientDAO {

    List<PersonalClient> findAll();

    PersonalClient findByFullName(String fullName);
    
    PersonalClient findByUsername(String username);

    void save(PersonalClient client);
    
    void delete(PersonalClient client);

}
