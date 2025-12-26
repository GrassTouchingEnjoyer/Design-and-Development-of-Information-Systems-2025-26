package back_end.model;

/**
 *Τραπεζικός λογαριασμός
 * 
 *Η κλάση αναπαριστά έναν τραπεζικό λογαριασμό του συστήματος Bank Of TUC.
 *Χρησιμοποιεί το Builder Design Pattern για ευέλικτη και ασφαλή δημιουργία αντικειμένων.
 * 
 *Σημαντικές επιλογές σχεδίασης:
 *- Τα πεδία accountId, ownerUsername και type είναι final → δεν αλλάζουν ποτέ μετά τη δημιουργία.
 *- Το υπόλοιπο ξεκινάει πάντα από 0.0 (σύμφωνα με την τελευταία σας απόφαση).
 *- Το αρχικό υπόλοιπο (αν υπάρχει) προστίθεται μετά τη δημιουργία με deposit().
 *- Αυτό επιτρέπει καλύτερο διαχωρισμό: η δημιουργία λογαριασμού είναι ξεχωριστή από την αρχικοποίηση υπολοίπου.
 */
public class Account {

    //Αμετάβλητα χαρακτηριστικά του λογαριασμού (immutable)
    private final String accountId;         //Μοναδικός κωδικός λογαριασμού (π.χ. IBAN-like)
    private final String ownerUsername;     //Username του κατόχου (σύνδεση με User)
    private final String type;              //Τύπος λογαριασμού (Current, Savings, Business κ.λπ.)

    //Μεταβλητό χαρακτηριστικό - αλλάζει με συναλλαγές
    private double currentBalance = 0.0;    //Τρέχον υπόλοιπο. Ξεκινάει πάντα από μηδέν.

    /**
     *Ιδιωτικός constructor.
     *Προσβάσιμος μόνο από τον εσωτερικό Builder για να εξασφαλίσουμε σωστή δημιουργία.
     */
    private Account(AccountBuilder builder) {
        this.accountId = builder.accountId;
        this.ownerUsername = builder.ownerUsername;
        this.type = builder.type;
    }

    //========================= GETTERS =========================

    /**
     *Επιστρέφει τον κωδικό του λογαριασμού.
     */
    public String getAccountId() {
        return accountId;
    }

    /**
     * Επιστρέφει το username του κατόχου.
     */
    public String getOwnerUsername() {
        return ownerUsername;
    }

    /**
     *Επιστρέφει το τρέχον υπόλοιπο.
     */
    public double getCurrentBalance() {
        return currentBalance;
    }

    /**
     *Επιστρέφει τον τύπο του λογαριασμού.
     */
    public String getType() {
        return type;
    }

    //========================= ΣΥΝΑΛΛΑΓΕΣ =========================

    /**
     *Εκτελεί κατάθεση ποσού στον λογαριασμό.
     * 
     *@param amount το ποσό που κατατίθεται (πρέπει να είναι > 0)
     */
    public void deposit(double amount) {
        if (amount > 0) {
            currentBalance += amount;
            System.out.println("✔ Κατάθεση " + amount + " € στον λογαριασμό " + accountId +
                               " | Νέο υπόλοιπο: " + currentBalance + " €");
        } else {
            System.out.println("✖ Μη έγκυρο ποσό κατάθεσης (πρέπει να είναι θετικό)");
        }
    }

    /**
     *Εκτελεί ανάληψη ποσού από τον λογαριασμό.
     * 
     *@param amount το ποσό που αναλαμβάνεται (πρέπει να είναι > 0 και <= τρέχον υπόλοιπο)
     */
    public void withdraw(double amount) {
        if (amount > 0 && amount <= currentBalance) {
            currentBalance -= amount;
            System.out.println("✔ Ανάληψη " + amount + " € από τον λογαριασμό " + accountId +
                               " | Νέο υπόλοιπο: " + currentBalance + " €");
        } else if (amount <= 0) {
            System.out.println("✖ Μη έγκυρο ποσό ανάληψης (πρέπει να είναι θετικό)");
        } else {
            System.out.println("✖ Ανεπαρκές υπόλοιπο για ανάληψη " + amount + " €");
        }
    }

    /**
     *Επιστρέφει string αναπαράσταση του λογαριασμού (χρήσιμο για debugging / logging).
     */
    @Override
    public String toString() {
        return "Account{" +
               "accountId='" + accountId + '\'' +
               ", ownerUsername='" + ownerUsername + '\'' +
               ", currentBalance=" + currentBalance +
               ", type='" + type + '\'' +
               '}';
    }

    //===================== BUILDER PATTERN =====================

    /**
     *Εσωτερική στατική κλάση που υλοποιεί το Builder Design Pattern.
     *Επιτρέπει fluent και ασφαλή δημιουργία Account αντικειμένων.
     */
    public static class AccountBuilder {

        //Υποχρεωτικά πεδία
        private final String accountId;
        private final String ownerUsername;

        //Προαιρετικό πεδίο με default τιμή
        private String type = "Current";

        /**
         *Constructor του Builder με τα υποχρεωτικά πεδία.
         * 
         *@param accountId      μοναδικός κωδικός λογαριασμού
         *@param ownerUsername  username του κατόχου
         */
        public AccountBuilder(String accountId, String ownerUsername) {
            this.accountId = accountId;
            this.ownerUsername = ownerUsername;
        }

        /**
         *Ορίζει τον τύπο λογαριασμού (fluent method).
         * 
         *@param type ο τύπος (π.χ. "Savings", "Business")
         *@return ο ίδιος ο Builder για chaining
         */
        public AccountBuilder type(String type) {
            this.type = type;
            return this;
        }

        /**
         *Δημιουργεί και επιστρέφει το τελικό Account αντικείμενο.
         * 
         *@return νέο Account με τις τιμές που ορίστηκαν
         */
        public Account build() {
            return new Account(this);
        }
    }
}
