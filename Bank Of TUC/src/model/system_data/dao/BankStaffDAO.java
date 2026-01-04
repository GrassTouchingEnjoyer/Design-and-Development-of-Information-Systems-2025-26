package model.system_data.dao;

import java.util.List;

import model.actors.BankStaff;

public interface BankStaffDAO {

    List<BankStaff> findAll();

    BankStaff findByFullName(String fullName);

	void delete(BankStaff staff);

	void add(BankStaff staff);

	BankStaff findByAfm(String afm);

	BankStaff findByUsername(String username);
}
