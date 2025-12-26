package back_end.actors;

public abstract class Administrator extends User {
    
    protected String employeeId;
    protected String role;
    protected String managementLevel;
    
    public Administrator(String username, String password, String employeeId, String role, String managementLevel) {
        super(username, password);
        this.employeeId = employeeId;
        this.role = role;
        this.managementLevel = managementLevel;
    }
    
    @Override
    public void viewAccountBalance() {
        System.out.println("Διαχειριστής (" + role + ") βλέπει συνολικά στοιχεία");
    }
    
    @Override
    public void performTransaction(double amount, String transactionType) {
        System.out.println("Διαχειριστής εφαρμόζει: " + transactionType);
    }
    
    @Override
    public void viewTransactionHistory() {
        System.out.println("Προβολή αρχείων προμηθειών και τόκων");
    }
    
    public void applyAnnualInterest() {
        System.out.println("Εφαρμογή ετήσιων τόκων σε όλους τους λογαριασμούς");
    }
    
    public void generateFeeReport() {
        System.out.println("Αναφορά εσόδων από προμήθειες");
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public String getRole() {
        return role;
    }

    public String getManagementLevel() {
        return managementLevel;
    }
}