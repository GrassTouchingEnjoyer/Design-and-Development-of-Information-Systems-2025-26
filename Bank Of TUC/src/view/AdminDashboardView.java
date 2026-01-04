package view;
import javax.swing.*;
import java.awt.*;

public class AdminDashboardView extends JPanel {

    private JLabel adminNameLabel;
    private JLabel titleLabel;
    private JTabbedPane tabbedPane;
    private JButton logoutButton;

    // TextFields ανά tab
    private JTextField clientUsernameField;
    private JTextField clientNameField;
    private JTextField clientPasswordField;
    private JButton clientCreateButton;

    private JTextField businessUsernameField;
    private JTextField businessNameField;
    private JTextField businessPasswordField;
    private JButton businessCreateButton;

    private JTextField staffUsernameField;
    private JTextField staffNameField;
    private JTextField staffPasswordField;
    private JButton staffCreateButton;
    private JButton deleteUserButton;

    public AdminDashboardView() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        Font labelFont = new Font("Arial", Font.BOLD, 16);

        // ===== TOP PANEL =====
     
        JPanel topPanel = new JPanel(new BorderLayout());

        // Αριστερά: Admin Name
        adminNameLabel = new JLabel("Admin Name: ", SwingConstants.LEFT);
        adminNameLabel.setFont(labelFont);
        adminNameLabel.setBorder(BorderFactory.createTitledBorder("Admin"));
        topPanel.add(adminNameLabel, BorderLayout.WEST);

        // Κέντρο: Title
        titleLabel = new JLabel("Δημιουργία Χρήστη", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 22));
        topPanel.add(titleLabel, BorderLayout.CENTER);

        // Δεξιά: Delete User button
        deleteUserButton = new JButton("Διαγραφή χρήστη");
        deleteUserButton.setFont(labelFont);
        topPanel.add(deleteUserButton, BorderLayout.EAST);

        add(topPanel, BorderLayout.NORTH);


        // ===== TAB PANEL =====
        tabbedPane = new JTabbedPane();

        tabbedPane.addTab("Πελάτης", createUserPanel("client"));
        tabbedPane.addTab("Επιχείρηση", createUserPanel("business"));
        tabbedPane.addTab("Προσωπικό", createUserPanel("staff"));

        add(tabbedPane, BorderLayout.CENTER);

        // ===== BOTTOM PANEL =====
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        logoutButton = new JButton("Log out");
        logoutButton.setFont(labelFont);
        bottomPanel.add(logoutButton);

        add(bottomPanel, BorderLayout.SOUTH);
    }

    // ===== Δημιουργία Panel ανά τύπο χρήστη =====
    private JPanel createUserPanel(String type) {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        Font labelFont = new Font("Arial", Font.BOLD, 16);
        Font fieldFont = new Font("Arial", Font.PLAIN, 16);

        // Username
        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(new JLabel("Username:"), gbc);
        JTextField usernameField = new JTextField(20);
        usernameField.setFont(fieldFont);
        gbc.gridx = 1;
        panel.add(usernameField, gbc);

        // Name
        gbc.gridx = 0; gbc.gridy++;
        panel.add(new JLabel("Όνομα:"), gbc);
        JTextField nameField = new JTextField(20);
        nameField.setFont(fieldFont);
        gbc.gridx = 1;
        panel.add(nameField, gbc);

        // Password
        gbc.gridx = 0; gbc.gridy++;
        panel.add(new JLabel("Κωδικός:"), gbc);
        JTextField passwordField = new JTextField(20);
        passwordField.setFont(fieldFont);
        gbc.gridx = 1;
        panel.add(passwordField, gbc);

        // Button
        gbc.gridx = 0; gbc.gridy++;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        JButton createButton = new JButton("Δημιουργία");
        createButton.setFont(labelFont);
        panel.add(createButton, gbc);

        // Σύνδεση με fields
        switch (type) {
            case "client":
                clientUsernameField = usernameField;
                clientNameField = nameField;
                clientPasswordField = passwordField;
                clientCreateButton = createButton;
                break;
            case "business":
                businessUsernameField = usernameField;
                businessNameField = nameField;
                businessPasswordField = passwordField;
                businessCreateButton = createButton;
                break;
            case "staff":
                staffUsernameField = usernameField;
                staffNameField = nameField;
                staffPasswordField = passwordField;
                staffCreateButton = createButton;
                break;
        }

        return panel;
    }

    // ===== Getters =====
    public JLabel getAdminNameLabel() { return adminNameLabel; }
    public JButton getLogoutButton() { return logoutButton; }

    public JButton getClientCreateButton() { return clientCreateButton; }
    public JButton getBusinessCreateButton() { return businessCreateButton; }
    public JButton getStaffCreateButton() { return staffCreateButton; }
    public JButton getDeleteUserButton() { return deleteUserButton; }

    public JTextField getClientUsernameField() { return clientUsernameField; }
    public JTextField getClientNameField() { return clientNameField; }
    public JTextField getClientPasswordField() { return clientPasswordField; }

    public JTextField getBusinessUsernameField() { return businessUsernameField; }
    public JTextField getBusinessNameField() { return businessNameField; }
    public JTextField getBusinessPasswordField() { return businessPasswordField; }

    public JTextField getStaffUsernameField() { return staffUsernameField; }
    public JTextField getStaffNameField() { return staffNameField; }
    public JTextField getStaffPasswordField() { return staffPasswordField; }

    public JTabbedPane getTabbedPane() { return tabbedPane; }
    
 // Μέσα στο AdminDashboardView
    public void showAdminFeatures(boolean isAdmin) {
        // Διαγραφή χρήστη
        deleteUserButton.setVisible(isAdmin);

        // Tab Προσωπικό
        enableTab("Προσωπικό", isAdmin);
    }
    
    public void enableTab(String title, boolean enable) {
        int index = tabbedPane.indexOfTab(title);
        if (index != -1) {
            tabbedPane.setEnabledAt(index, enable);
            if (!enable && tabbedPane.getSelectedIndex() == index) {
                tabbedPane.setSelectedIndex(0);
            }
        }
    }



    public void clearAllFields() {
        clientUsernameField.setText("");
        clientNameField.setText("");
        clientPasswordField.setText("");

        businessUsernameField.setText("");
        businessNameField.setText("");
        businessPasswordField.setText("");

        staffUsernameField.setText("");
        staffNameField.setText("");
        staffPasswordField.setText("");
    }
}

