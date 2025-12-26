package back_end.actors;

/**
 *Κλάση Λογαριασμού με Builder Pattern
 * 
 *Χρησιμοποιείται για την ασφαλή και ευέλικτη δημιουργία λογαριασμών
 *με υποχρεωτικά και προαιρετικά πεδία.
 */
public class Account {
    private final String accountId;      //Υποχρεωτικό
    private final String ownerUsername;  //Υποχρεωτικό
    private final double initialBalance; //Προαιρετικό (default 0.0)
    private final String type;           //Προαιρετικό (default "Current")
    private final String currency;       //Σταθερό: EUR (από παραδοχές)

    //Private constructor - μόνο ο Builder μπορεί να τον καλέσει
    private Account(AccountBuilder builder) {
        this.accountId = builder.accountId;
        this.ownerUsername = builder.ownerUsername;
        this.initialBalance = builder.initialBalance;
        this.type = builder.type;
        this.currency = "EUR";
    }

    // Getters
    public String getAccountId() { return accountId; }
    public String getOwnerUsername() { return ownerUsername; }
    public double getBalance() { return initialBalance; } //Για απλότητα, εδώ στατικό υπόλοιπο
    public String getType() { return type; }
    public String getCurrency() { return currency; }

    //Μέθοδοι συναλλαγών (για demo)
    private double balance = initialBalance;
    public void deposit(double amount) {
        balance += amount;
        System.out.println("Κατάθεση " + amount + "€ στον λογαριασμό " + accountId);
    }
    public void withdraw(double amount) {
        if (amount <= balance) {
            balance -= amount;
            System.out.println("Ανάληψη " + amount + "€ από τον λογαριασμό " + accountId);
        } else {
            System.out.println("Ανεπαρκές υπόλοιπο!");
        }
    }
    public double getCurrentBalance() { return balance; }

    //Εμφάνιση πληροφοριών
    @Override
    public String toString() {
        return "Account{" +
                "id='" + accountId + '\'' +
                ", owner='" + ownerUsername + '\'' +
                ", balance=" + balance +
                ", type='" + type + '\'' +
                ", currency='" + currency + '\'' +
                '}';
    }

    //==================== BUILDER CLASS ====================
    public static class AccountBuilder {
        private final String accountId;         //Υποχρεωτικό
        private final String ownerUsername;     //Υποχρεωτικό
        private double initialBalance = 0.0;    //Προαιρετικό
        private String type = "Current";        //Προαιρετικό

        //Constructor με υποχρεωτικά πεδία
        public AccountBuilder(String accountId, String ownerUsername) {
            this.accountId = accountId;
            this.ownerUsername = ownerUsername;
        }

        //Προαιρετικές μέθοδοι (fluent interface)
        public AccountBuilder initialBalance(double balance) {
            this.initialBalance = balance;
            return this;
        }

        public AccountBuilder type(String type) {
            this.type = type; //π.χ. "Savings", "Business", "Current"
            return this;
        }

        //Τελική μέθοδος δημιουργίας
        public Account build() {
            return new Account(this);
        }
    }
}

