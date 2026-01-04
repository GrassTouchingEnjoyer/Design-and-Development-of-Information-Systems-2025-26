package model.system_data.dao;

import java.util.List;

import model.actors.BankStaff;
import model.system_data.persistence.FileDataStore;

public class FileBankStaffDAO implements BankStaffDAO{
	
	private final List<BankStaff> staff;

    public FileBankStaffDAO() {
        this.staff = FileDataStore.getInstance().getBankStaff();
    }

	@Override
	public List<BankStaff> findAll() {
		return List.copyOf(staff);
	}

	@Override
	public BankStaff findByFullName(String fullName) {
		return staff.stream()
                .filter(c -> c.getFullName().equals(fullName))
                .findFirst()
                .orElse(null);
	}
	
	@Override
	public BankStaff findByUsername(String username) {
		return staff.stream()
                .filter(c -> c.getUsername().equals(username))
                .findFirst()
                .orElse(null);
	}

	@Override
	public void delete(BankStaff bstaff) {
		staff.remove(bstaff);		
	}

	@Override
	public void add(BankStaff bstaff) {
		staff.add(bstaff);
	}

	@Override
	public BankStaff findByAfm(String afm) {
		return staff.stream()
                .filter(c -> c.getAfm().equals(afm))
                .findFirst()
                .orElse(null);
	}

}
