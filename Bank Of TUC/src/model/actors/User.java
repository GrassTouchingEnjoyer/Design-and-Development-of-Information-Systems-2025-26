package model.actors;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public abstract class User implements Serializable{
    
    protected String username;
    protected String password;
    
    protected String fullName;
    protected String afm;
    protected String phone;
    
    
    public User(String username, String password, String fullName, String afm, String phone) {
        this.username = username;
        this.password = password;
        
        this.fullName = fullName;
        this.afm = afm;
        this.phone = phone;
    }
    
    public abstract UserRole getRole();
    
    
    public String getUsername() {
        return username;
    }

	public String getFullName() {
		return fullName;
	}

	public String getAfm() {
		return afm;
	}

	public String getPassword() {
		return password;
	}

	public String getPhone() {
		return phone;
	}
	
	public enum UserRole {
	    PERSONAL,
	    BUSINESS,
	    ADMIN,
	    STAFF
	}
	
	
	
	
	
}