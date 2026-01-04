package model.actors;

public class BankStaff extends User {

	public BankStaff(String username, String password, String fullName, String afm, String phone) {
		super(username, password, fullName, afm, phone);
		// TODO Auto-generated constructor stub
	}

	@Override
	public UserRole getRole() {
		// TODO Auto-generated method stub
		return UserRole.STAFF;
	}
    
	@Override
	public String toString() {
	    return username;
	}
    
    

    

}