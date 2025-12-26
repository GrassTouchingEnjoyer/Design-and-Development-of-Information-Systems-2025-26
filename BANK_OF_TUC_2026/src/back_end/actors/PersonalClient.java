package back_end.actors;

public class PersonalClient extends Client {

    public PersonalClient(String username, String password, String fullName, String afm, String phone) {
        super(username, password, fullName, afm, phone);
    }

    @Override
    public String toString() {
        return "PersonalClient{" +
               "username='" + getUsername() + '\'' +
               ", fullName='" + getFullName() + '\'' +
               ", afm='" + getAfm() + '\'' +
               ", phone='" + getPhone() + '\'' +
               '}';
    }

}
