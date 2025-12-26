package back_end.actors;

public abstract class Client extends User {
    
    protected String fullName;
    protected String afm;
    protected String phone;
    
    public Client(String username, String password, String fullName, String afm, String phone) {
        super(username, password);
        this.fullName = fullName;
        this.afm = afm;
        this.phone = phone;
    }
    public String getFullName() {
        return fullName;
    }

    public String getAfm() {
        return afm;
    }

    public String getPhone() {
        return phone;
    }
    
    @Override
    public void viewAccountBalance() {
        System.out.println("Πελάτης: " + fullName);
        for (Account acc : accounts) {
            System.out.println("   Λογαριασμός " + acc.getAccountId() + ": " + acc.getBalance() + " €");
        }
    }
    
    
    
    @Override
    public void performTransaction(double amount, String transactionType) {
        System.out.println(fullName + " → " + transactionType + ": " + amount + " €");
    }
    
    @Override
    public void viewTransactionHistory() {
        System.out.println("Ιστορικό συναλλαγών για " + fullName);
    }

    

}
