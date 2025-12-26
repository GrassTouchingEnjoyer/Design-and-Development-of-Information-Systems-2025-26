package back_end.actors;

public class BankStaff extends User {
    
    protected String employeeId;
    protected String department;
    
    public BankStaff(String username, String password, String employeeId, String department) {
        super(username, password);
        this.employeeId = employeeId;
        this.department = department;
    }
    public String getEmployeeId() {
        return employeeId;
    }

    public String getDepartment() {
        return department;
    }
    
    @Override
    public void viewAccountBalance() {
        System.out.println("Προσωπικό (" + department + ") βλέπει λογαριασμούς πελατών");
    }
    
    @Override
    public void performTransaction(double amount, String transactionType) {
        System.out.println("Προσωπικό εκτελεί: " + transactionType + " " + amount + " €");
    }
    
    @Override
    public void viewTransactionHistory() {
        System.out.println("Προσωπικό βλέπει ιστορικό για υποστήριξη");
    }
    
    public void assistClient(String clientUsername) {
        System.out.println("Υποστήριξη πελάτη " + clientUsername + " από " + department);
    }
    
    @Override
    public String toString() {
    return "BankStaff{" +
           "username='" + getUsername() + '\'' +
           ", employeeId='" + getEmployeeId() + '\'' +
           ", department='" + getDepartment() + '\'' +
           '}';
}

}
