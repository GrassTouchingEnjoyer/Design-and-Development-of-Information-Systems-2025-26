package model.actors;

public class BusinessClient extends Client {
    
    private BusinessAccount account;
    
    
    public BusinessClient(String username, String password, String fullName, String afm,
    					  String phone) {
        
    	super(username, password,fullName,afm,phone);
    }


	public BusinessAccount getAccount() {
		return account;
	}

	public void setAccount(BusinessAccount account) {
		this.account = account;
	}

	@Override
	public String toString() {
	    return username;
	}

	@Override
	public UserRole getRole() {
		// TODO Auto-generated method stub
		return UserRole.BUSINESS;
	}


	
	
    
}