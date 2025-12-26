package back_end.actors;

public class BusinessClient extends User {
    
    protected String companyName;
    protected String vatNumber;
    protected String representative;
    
    public BusinessClient(String username, String password, String companyName, String vatNumber, String representative) {
        super(username, password);
        this.companyName = companyName;
        this.vatNumber = vatNumber;
        this.representative = representative;
    }
    
    @Override
    public void viewAccountBalance() {
        System.out.println("Επιχείρηση: " + companyName);
        for (Account acc : accounts) {
            System.out.println("   Λογαριασμός " + acc.getAccountId() + ": " + acc.getBalance() + " €");
        }
    }
    
    @Override
    public void performTransaction(double amount, String transactionType) {
        System.out.println(companyName + " → " + transactionType + ": " + amount + " €");
    }
    
    @Override
    public void viewTransactionHistory() {
        System.out.println("Ιστορικό συναλλαγών για " + companyName);
    }
    
    public void performBulkPayment() {
        System.out.println("Μαζική πληρωμή από " + companyName);
    }
    public String getCompanyName() {
        return companyName;
    }

    public String getVatNumber() {
        return vatNumber;
    }

    public String getRepresentative() {
        return representative;
    }
}