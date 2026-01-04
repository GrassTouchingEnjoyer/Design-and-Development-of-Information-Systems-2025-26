package model.actors;

public class Administrator extends User {
	
	private static final long serialVersionUID = 1L;

	public Administrator(String username, String password, String fullName, String afm, String phone) {
		super(username, password, fullName, afm, phone);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "Administrator\nusername=" + username + "\npassword=" + password + "\nfullName=" + fullName + "\nafm="
				+ afm + "\nphone=" + phone + "\n\n";
	}

	@Override
	public UserRole getRole() {
		return UserRole.ADMIN;
	}
    
    
}