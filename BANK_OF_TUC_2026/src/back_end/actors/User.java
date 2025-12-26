package back_end.actors;

import java.util.ArrayList;
import java.util.List;

/**
 * Abstract βάση για όλους τους actors
 */
public abstract class User {
    
    protected String username;
    protected String password;
    protected List<Account> accounts;
    protected boolean authenticated;
    
    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.accounts = new ArrayList<>();
        this.authenticated = false;
    }
    
    public boolean login(String enteredUsername, String enteredPassword) {
        if (this.username.equals(enteredUsername) && this.password.equals(enteredPassword)) {
            this.authenticated = true;
            System.out.println("[LOGIN] Επιτυχής σύνδεση: " + username);
            return true;
        }
        System.out.println("[LOGIN] Αποτυχία σύνδεσης");
        return false;
    }
    
    public void logout() {
        this.authenticated = false;
        System.out.println("[LOGOUT] Αποσύνδεση: " + username);
    }
    
    public abstract void viewAccountBalance();
    public abstract void performTransaction(double amount, String transactionType);
    public abstract void viewTransactionHistory();
    
    public void addAccount(Account account) {
        accounts.add(account);
    }
    
    public List<Account> getAccounts() {
        return accounts;
    }
    
    public String getUsername() {
        return username;
    }
    
    public boolean isAuthenticated() {
        return authenticated;
    }
}