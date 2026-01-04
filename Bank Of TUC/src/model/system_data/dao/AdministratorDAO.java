package model.system_data.dao;

import java.util.List;

import model.actors.Administrator;


public interface AdministratorDAO {

    List<Administrator> findAll();

    Administrator findByFullName(String fullName);

	void delete(Administrator admin);

	void add(Administrator admin);

	Administrator findByAfm(String afm);

	Administrator findByUsername(String username);
}
