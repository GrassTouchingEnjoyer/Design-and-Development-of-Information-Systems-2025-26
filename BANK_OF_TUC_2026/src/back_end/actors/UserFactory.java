package back_end.actors;

/**
 * Factory Method Design Pattern
 * 
 * Κεντρική κλάση για τη δημιουργία όλων των τύπων χρηστών (actors) του συστήματος.
 * Αποκρύπτει την πολυπλοκότητα δημιουργίας και επιτρέπει εύκολη επέκταση με νέους ρόλους.
 */
public class UserFactory {

    /**
     * Στατική μέθοδος δημιουργίας χρήστη βάσει ρόλου
     * 
     * @param role    Ο ρόλος του χρήστη (π.χ. "personal", "business", "staff", "manager")
     * @param username Το username για login
     * @param password Το password για login
     * @param params   Επιπλέον παράμετροι ανάλογα με τον ρόλο (varargs για ευελιξία)
     * @return         Αντικείμενο User (κατάλληλη υποκλάση)
     */
    public static User createUser(String role, String username, String password, String... params) {
        
        // Χρήση switch expression για καθαρότητα και ασφάλεια
        return switch (role.toLowerCase()) {
            case "personal", "client" -> {
                // Απαιτούνται 3 παράμετροι: fullName, afm, phone
                if (params.length < 3) throw new IllegalArgumentException("PersonalClient: απαιτούνται fullName, afm, phone");
                yield new PersonalClient(username, password, params[0], params[1], params[2]);
            }
            
            case "business", "enterprise" -> {
                // Απαιτούνται 3 παράμετροι: companyName, vatNumber, representative
                if (params.length < 3) throw new IllegalArgumentException("BusinessClient: απαιτούνται companyName, vatNumber, representative");
                yield new BusinessClient(username, password, params[0], params[1], params[2]);
            }
            
            case "staff" -> {
                // Απαιτούνται 2 παράμετροι: employeeId, department
                if (params.length < 2) throw new IllegalArgumentException("BankStaff: απαιτούνται employeeId, department");
                yield new BankStaff(username, password, params[0], params[1]);
            }
            
            case "manager", "admin", "director" -> {
                // Απαιτούνται 3 παράμετροι: employeeId, role, managementLevel
                if (params.length < 3) throw new IllegalArgumentException("Manager: απαιτούνται employeeId, role, managementLevel");
                yield new Manager(username, password, params[0], params[1], params[2]);
            }
            
            default -> throw new IllegalArgumentException("Άγνωστος ρόλος: " + role +
                    ". Υποστηριζόμενοι: personal, business, staff, manager");
        };
    }
}