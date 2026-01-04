package view;
import javax.swing.*;
import java.awt.*;

public class LoginView extends JPanel {

    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;

    public LoginView() {
        setLayout(new BorderLayout());

        // ---------- Αριστερό Panel (Login Form) ----------
        JPanel loginPanel = new JPanel(new GridBagLayout());
        loginPanel.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 10, 15, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;        
        
        
        JLabel TITLE = new JLabel("BANK OF TUC");
        TITLE.setFont(new Font("Times New Roman", Font.BOLD, 40));

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        loginPanel.add(TITLE, gbc);


        JLabel titleLabel = new JLabel("Σύνδεση Χρήστη");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        gbc.gridy = 1;
        loginPanel.add(titleLabel, gbc);

        gbc.gridwidth = 1;

        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        gbc.gridx = 0;
        gbc.gridy = 2;          
        loginPanel.add(usernameLabel, gbc);

        usernameField = new JTextField(15);
        usernameField.setFont(new Font("Arial", Font.PLAIN, 18));
        gbc.gridx = 1;
        loginPanel.add(usernameField, gbc);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        gbc.gridx = 0;
        gbc.gridy = 3;
        loginPanel.add(passwordLabel, gbc);

        passwordField = new JPasswordField(15);
        passwordField.setFont(new Font("Arial", Font.PLAIN, 18));
        gbc.gridx = 1;
        loginPanel.add(passwordField, gbc);

        loginButton = new JButton("Login");
        loginButton.setFont(new Font("Arial", Font.BOLD, 18));
        gbc.gridx = 0;
        gbc.gridy = 4;          
        gbc.gridwidth = 2;
        loginPanel.add(loginButton, gbc);
        
        

        // ---------- Δεξί Panel (Εικόνα) ----------

        JPanel imagePanel = new ImagePanel("resources/icarus_Bank_of_TUC.png");

        // ---------- Split Pane ----------
        JSplitPane splitPane = new JSplitPane(
                JSplitPane.HORIZONTAL_SPLIT,
                loginPanel,
                imagePanel
        );
        splitPane.setDividerLocation(450);
        splitPane.setDividerSize(2);
        splitPane.setEnabled(false);

        add(splitPane, BorderLayout.CENTER);
    }
    
    public void clearFields() {
        usernameField.setText("");
        passwordField.setText("");
    }

    // Getters για Controller
    public JTextField getUsernameField() {
        return usernameField;
    }

    public JPasswordField getPasswordField() {
        return passwordField;
    }

    public JButton getLoginButton() {
        return loginButton;
    }
}