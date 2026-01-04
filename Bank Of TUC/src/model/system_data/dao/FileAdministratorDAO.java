package model.system_data.dao;

import java.util.List;

import model.actors.Administrator;
import model.system_data.persistence.FileDataStore;

public class FileAdministratorDAO implements AdministratorDAO{
	
	private final List<Administrator> admin;

    public FileAdministratorDAO() {
        this.admin = FileDataStore.getInstance().getAdmins();
    }

	@Override
	public List<Administrator> findAll() {
		return List.copyOf(admin);
	}

	@Override
	public Administrator findByFullName(String fullName) {
		return admin.stream()
                .filter(c -> c.getFullName().equals(fullName))
                .findFirst()
                .orElse(null);
	}
	
	@Override
	public Administrator findByUsername(String username) {
		return admin.stream()
                .filter(c -> c.getUsername().equals(username))
                .findFirst()
                .orElse(null);
	}

	@Override
	public void delete(Administrator administrator) {
		admin.remove(administrator);		
	}

	@Override
	public void add(Administrator administrator) {
		admin.add(administrator);
	}

	@Override
	public Administrator findByAfm(String afm) {
		return admin.stream()
                .filter(c -> c.getAfm().equals(afm))
                .findFirst()
                .orElse(null);
	}

}
