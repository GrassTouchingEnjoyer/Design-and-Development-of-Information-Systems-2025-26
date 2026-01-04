package view;
import javax.swing.*;
import java.awt.*;

public class TransferView extends JPanel {

    private JTextField ibanField;
    private JTextField amountField;
    private JComboBox<String> transferTypeCombo;
    private JTextField extraField;
    private JLabel extraLabel;

    private JButton transferButton;
    private JButton backButton;

    public TransferView() {
        setLayout(new BorderLayout(30, 30));
        setBorder(BorderFactory.createEmptyBorder(40, 60, 40, 60));

        Font labelFont = new Font("Arial", Font.PLAIN, 18);
        Font fieldFont = new Font("Arial", Font.PLAIN, 18);
        Font titleFont = new Font("Arial", Font.BOLD, 26);
        Font buttonFont = new Font("Arial", Font.BOLD, 18);

        JLabel title = new JLabel("Μεταφορά Χρημάτων", SwingConstants.CENTER);
        title.setFont(titleFont);

        // -------- FORM PANEL --------
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(12, 12, 12, 12);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1;

        // IBAN
        gbc.gridx = 0; gbc.gridy = 0;
        JLabel ibanLabel = new JLabel("IBAN:");
        ibanLabel.setFont(labelFont);
        formPanel.add(ibanLabel, gbc);

        gbc.gridx = 1;
        ibanField = new JTextField(22);
        ibanField.setFont(fieldFont);
        formPanel.add(ibanField, gbc);

        // Amount
        gbc.gridx = 0; gbc.gridy = 1;
        JLabel amountLabel = new JLabel("Χρηματικό Ποσό:");
        amountLabel.setFont(labelFont);
        formPanel.add(amountLabel, gbc);

        gbc.gridx = 1;
        amountField = new JTextField(12);
        amountField.setFont(fieldFont);
        formPanel.add(amountField, gbc);

        // EXTRA FIELD (BIC / SWIFT) – ΠΑΝΩ από ComboBox
        gbc.gridx = 0; gbc.gridy = 2;
        extraLabel = new JLabel("BIC:");
        extraLabel.setFont(labelFont);
        formPanel.add(extraLabel, gbc);

        gbc.gridx = 1;
        extraField = new JTextField(18);
        extraField.setFont(fieldFont);
        formPanel.add(extraField, gbc);

        extraLabel.setVisible(false);
        extraField.setVisible(false);

        // TRANSFER TYPE COMBO (ΤΕΛΕΥΤΑΙΟ)
        gbc.gridx = 0; gbc.gridy = 3;
        JLabel typeLabel = new JLabel("Τύπος Συναλλαγής:");
        typeLabel.setFont(labelFont);
        formPanel.add(typeLabel, gbc);

        gbc.gridx = 1;
        transferTypeCombo = new JComboBox<>(
                new String[]{"Ενδοτραπεζική συναλλαγή", "SEPA", "SWIFT"}
        );
        transferTypeCombo.setFont(fieldFont);
        formPanel.add(transferTypeCombo, gbc);

        // -------- BUTTONS --------
        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 10));
        transferButton = new JButton("Μεταφορά");
        backButton = new JButton("Πίσω");

        transferButton.setFont(buttonFont);
        backButton.setFont(buttonFont);

        buttonsPanel.add(transferButton);
        buttonsPanel.add(backButton);

        add(title, BorderLayout.NORTH);
        add(formPanel, BorderLayout.CENTER);
        add(buttonsPanel, BorderLayout.SOUTH);

        // -------- COMBO LOGIC --------
        transferTypeCombo.addActionListener(e -> updateExtraField());
    }

    private void updateExtraField() {
        String selected = (String) transferTypeCombo.getSelectedItem();

        if ("SEPA".equals(selected)) {
            extraLabel.setText("BIC:");
            extraLabel.setVisible(true);
            extraField.setVisible(true);
        } else if ("SWIFT".equals(selected)) {
            extraLabel.setText("Swift Code:");
            extraLabel.setVisible(true);
            extraField.setVisible(true);
        } else {
            extraLabel.setVisible(false);
            extraField.setVisible(false);
        }

        revalidate();
        repaint();
    }
    
    public void clearFields() {
        ibanField.setText("");
        amountField.setText("");
        extraField.setText("");

        // Επαναφορά ComboBox στην πρώτη επιλογή
        transferTypeCombo.setSelectedIndex(0);

        // Απόκρυψη extra πεδίου
        extraLabel.setVisible(false);
        extraField.setVisible(false);

        revalidate();
        repaint();
    }

    // -------- GETTERS --------
    public JButton getTransferButton() {
        return transferButton;
    }

    public JButton getBackButton() {
        return backButton;
    }

    public JTextField getIbanField() {
        return ibanField;
    }

    public JTextField getAmountField() {
        return amountField;
    }

    public JComboBox<String> getTransferTypeCombo() {
        return transferTypeCombo;
    }

    public JTextField getExtraField() {
        return extraField;
    }
}

