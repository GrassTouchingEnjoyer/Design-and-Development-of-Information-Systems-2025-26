package back_end.actors;

public class Manager extends Administrator {

    public Manager(String username, String password, String employeeId, String role, String managementLevel) {
        super(username, password, employeeId, role, managementLevel);
    }

    @Override
    public String toString() {
    return "Manager{" +
           "username='" + getUsername() + '\'' +
           ", employeeId='" + getEmployeeId() + '\'' +
           ", role='" + getRole() + '\'' +
           ", managementLevel='" + getManagementLevel() + '\'' +
           '}';
    }

}
