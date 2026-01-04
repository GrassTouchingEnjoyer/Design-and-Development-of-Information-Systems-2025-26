package view;
import javax.swing.*;
import java.awt.*;

public class IssueBillView extends JPanel {

    private JTextField paymentCodeField;
    private JTextField customerField;
    private JTextField amountField;
    private JTextField deadlineField;

    private JButton backButton;
    private JButton issueButton;

    public IssueBillView() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
     // ===== Title =====
        JLabel titleLabel = new JLabel("Έκδοση Λογαριασμού Πληρωμής", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 22));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 20, 10));

        add(titleLabel, BorderLayout.NORTH);


        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        Font labelFont = new Font("Arial", Font.BOLD, 16);
        Font fieldFont = new Font("Arial", Font.PLAIN, 16);

        // Row 0
        gbc.gridx = 0; gbc.gridy = 0;
        JLabel paymentCodeLabel = new JLabel("Κωδικός πληρωμής:");
        paymentCodeLabel.setFont(labelFont);
        formPanel.add(paymentCodeLabel, gbc);

        gbc.gridx = 1;
        paymentCodeField = new JTextField(20);
        paymentCodeField.setFont(fieldFont);
        formPanel.add(paymentCodeField, gbc);

        // Row 1
        gbc.gridx = 0; gbc.gridy++;
        JLabel customerLabel = new JLabel("Πελάτης:");
        customerLabel.setFont(labelFont);
        formPanel.add(customerLabel, gbc);

        gbc.gridx = 1;
        customerField = new JTextField(20);
        customerField.setFont(fieldFont);
        formPanel.add(customerField, gbc);

        // Row 2
        gbc.gridx = 0; gbc.gridy++;
        JLabel amountLabel = new JLabel("Ποσό πληρωμής:");
        amountLabel.setFont(labelFont);
        formPanel.add(amountLabel, gbc);

        gbc.gridx = 1;
        amountField = new JTextField(20);
        amountField.setFont(fieldFont);
        formPanel.add(amountField, gbc);

        // Row 3
        gbc.gridx = 0; gbc.gridy++;
        JLabel deadlineLabel = new JLabel("Προθεσμία πληρωμής:");
        deadlineLabel.setFont(labelFont);
        formPanel.add(deadlineLabel, gbc);

        gbc.gridx = 1;
        deadlineField = new JTextField(20);
        deadlineField.setFont(fieldFont);
        formPanel.add(deadlineField, gbc);

        add(formPanel, BorderLayout.CENTER);

        // Bottom buttons
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 20, 10));

        backButton = new JButton("Πίσω");
        issueButton = new JButton("Έκδοση");

        backButton.setFont(labelFont);
        issueButton.setFont(labelFont);

        bottomPanel.add(backButton);
        bottomPanel.add(issueButton);

        add(bottomPanel, BorderLayout.SOUTH);
    }

    // Getters
    public JButton getBackButton() {
        return backButton;
    }

    public JButton getIssueButton() {
        return issueButton;
    }

    public String getPaymentCode() {
        return paymentCodeField.getText();
    }

    public String getCustomer() {
        return customerField.getText();
    }

    public String getAmount() {
        return amountField.getText();
    }

    public String getDeadline() {
        return deadlineField.getText();
    }

    public void clearFields() {
        paymentCodeField.setText("");
        customerField.setText("");
        amountField.setText("");
        deadlineField.setText("");
    }
}

