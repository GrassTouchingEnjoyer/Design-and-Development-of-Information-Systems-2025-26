package model.actors;

import java.util.ArrayList;
import java.util.List;


public class PersonalClient extends Client {
	
	private ArrayList<PersonalAccount> accounts ;
	
    
	
	public PersonalClient(String username, String password, String fullName,
				  		  String afm, String phone) 
	{
	
		super(username, password, fullName, afm, phone);
		this.accounts = new ArrayList<>();
	
	}	
    
	
    public void addPersonalAccount(PersonalAccount acc) {
    	if(acc!=null) {
    		if (!accounts.contains(acc)) {
    			accounts.add(acc);
    		}
    	}
	}
    
    public void removePersonalAccount(PersonalAccount acc) {
    	if(acc!=null) {
    		if (accounts.contains(acc)) {
    			accounts.remove(acc);
    		}
    	}
	}

	public ArrayList<PersonalAccount> getAccounts() {

		return accounts;	
	}
	


	@Override
	public String toString() {
	    return username;
	}

	@Override
	public UserRole getRole() {
		// TODO Auto-generated method stub
		return UserRole.PERSONAL;
	}




	

}
