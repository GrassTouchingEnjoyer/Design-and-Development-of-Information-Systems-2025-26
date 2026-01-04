package view;

import javax.swing.*;

import java.awt.*;


import javax.swing.*;
import java.awt.*;

public class CreateStandingOrderView extends JPanel {

    // -------- CONSTANTS --------
    private static final String TRANSFER_CARD = "TRANSFER";
    private static final String PAYMENT_CARD = "PAYMENT";

    // ================= TRANSFER FIELDS =================
    private JTextField transferIbanField;
    private JTextField transferAmountField;
    private JTextField transferFrequencyField;
    private JTextField transferExecutionDayField;
    private JTextField transferBicSwiftField;
    private JTextField transferActiveUntilField;
    private JTextField transferNameField;
    private JTextArea  transferDescriptionArea;
    private JComboBox<String> transferTypeCombo;

    // ================= PAYMENT FIELDS =================
    private JTextField paymentActiveUntilField;
    private JTextField paymentMaxAmountField;
    private JTextField paymentNameField;
    private JTextArea  paymentDescriptionArea;
    private JTextField paymentCodeField;

    // ================= LAYOUT =================
    private JPanel cardsPanel;
    private CardLayout cardLayout;

    // ================= CONTROLS =================
    private JButton createButton;
    private JButton backButton;
    private JComboBox<String> typeCombo;

    public CreateStandingOrderView() {
        setLayout(new BorderLayout(20, 20));
        setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));

        Font labelFont = new Font("Arial", Font.PLAIN, 18);
        Font fieldFont = new Font("Arial", Font.PLAIN, 18);
        Font titleFont = new Font("Arial", Font.BOLD, 26);
        Font buttonFont = new Font("Arial", Font.BOLD, 18);

        // -------- TITLE --------
        JLabel title = new JLabel("Δημιουργία Πάγιας Εντολής", SwingConstants.CENTER);
        title.setFont(titleFont);
        add(title, BorderLayout.NORTH);

        // -------- CARDS --------
        cardLayout = new CardLayout();
        cardsPanel = new JPanel(cardLayout);

        cardsPanel.add(createTransferPanel(labelFont, fieldFont), TRANSFER_CARD);
        cardsPanel.add(createPaymentPanel(labelFont, fieldFont), PAYMENT_CARD);

        add(cardsPanel, BorderLayout.CENTER);

        // -------- BOTTOM --------
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));

        backButton = new JButton("Πίσω");
        createButton = new JButton("Δημιουργία");

        backButton.setFont(buttonFont);
        createButton.setFont(buttonFont);

        typeCombo = new JComboBox<>(new String[]{"Μεταφορά", "Πληρωμή"});
        typeCombo.setFont(fieldFont);
        typeCombo.addActionListener(e -> switchCard());

        bottomPanel.add(backButton);
        bottomPanel.add(typeCombo);
        bottomPanel.add(createButton);

        add(bottomPanel, BorderLayout.SOUTH);

        cardLayout.show(cardsPanel, TRANSFER_CARD);
    }

    // ==================================================
    // ================= TRANSFER CARD ==================
    // ==================================================
    private JPanel createTransferPanel(Font labelFont, Font fieldFont) {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Πάγια Μεταφορά"));

        GridBagConstraints gbc = baseGbc();
        int row = 0;

        transferIbanField = addField(panel, gbc, row++, "IBAN:", labelFont, fieldFont);
        transferAmountField = addField(panel, gbc, row++, "Χρηματικό Ποσό:", labelFont, fieldFont);
        transferFrequencyField = addField(panel, gbc, row++, "Συχνότητα (μήνες):", labelFont, fieldFont);
        transferExecutionDayField = addField(panel, gbc, row++, "Ημέρα Εκτέλεσης:", labelFont, fieldFont);

        gbc.gridx = 0;
        gbc.gridy = row;
        JLabel typeLabel = new JLabel("Τύπος Μεταφοράς:");
        typeLabel.setFont(labelFont);
        panel.add(typeLabel, gbc);

        gbc.gridx = 1;
        transferTypeCombo = new JComboBox<>(new String[]{
                "Ενδοτραπεζική Μεταφορά",
                "SEPA",
                "SWIFT"
        });
        transferTypeCombo.setFont(fieldFont);
        panel.add(transferTypeCombo, gbc);
        row++;

        transferBicSwiftField = addField(panel, gbc, row++, "BIC / Swift Code:", labelFont, fieldFont);
        transferActiveUntilField = addField(panel, gbc, row++, "Ενεργή έως (dd/MM/yyyy):", labelFont, fieldFont);
        transferNameField = addField(panel, gbc, row++, "Ονομασία:", labelFont, fieldFont);
        transferDescriptionArea = addTextArea(panel, gbc, row, "Περιγραφή:", labelFont, fieldFont);

        return panel;
    }

    // ==================================================
    // ================= PAYMENT CARD ===================
    // ==================================================
    private JPanel createPaymentPanel(Font labelFont, Font fieldFont) {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Πάγια Πληρωμή"));

        GridBagConstraints gbc = baseGbc();
        int row = 0;

        paymentActiveUntilField = addField(panel, gbc, row++, "Ενεργή έως (dd/MM/yyyy):", labelFont, fieldFont);
        paymentMaxAmountField = addField(panel, gbc, row++, "Μέγιστο Χρηματικό Ποσό:", labelFont, fieldFont);
        paymentNameField = addField(panel, gbc, row++, "Ονομασία:", labelFont, fieldFont);
        paymentDescriptionArea = addTextArea(panel, gbc, row++, "Περιγραφή:", labelFont, fieldFont);
        paymentCodeField = addField(panel, gbc, row, "Κωδικός Πληρωμής:", labelFont, fieldFont);

        return panel;
    }

    // ==================================================
    // ================= HELPERS ========================
    // ==================================================
    private void switchCard() {
        boolean isTransfer = typeCombo.getSelectedItem().equals("Μεταφορά");
        cardLayout.show(cardsPanel, isTransfer ? TRANSFER_CARD : PAYMENT_CARD);
    }

    private GridBagConstraints baseGbc() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1;
        return gbc;
    }

    private JTextField addField(JPanel panel, GridBagConstraints gbc, int row,
                               String label, Font labelFont, Font fieldFont) {
        gbc.gridx = 0;
        gbc.gridy = row;
        JLabel l = new JLabel(label);
        l.setFont(labelFont);
        panel.add(l, gbc);

        gbc.gridx = 1;
        JTextField field = new JTextField(18);
        field.setFont(fieldFont);
        panel.add(field, gbc);

        return field;
    }

    private JTextArea addTextArea(JPanel panel, GridBagConstraints gbc, int row,
                                 String label, Font labelFont, Font fieldFont) {
        gbc.gridx = 0;
        gbc.gridy = row;
        JLabel l = new JLabel(label);
        l.setFont(labelFont);
        panel.add(l, gbc);

        gbc.gridx = 1;
        JTextArea area = new JTextArea(3, 18);
        area.setFont(fieldFont);
        area.setLineWrap(true);
        area.setWrapStyleWord(true);
        panel.add(new JScrollPane(area), gbc);

        return area;
    }
    
    public void clearFields() {

        // ================= TRANSFER =================
        if (transferIbanField != null) transferIbanField.setText("");
        if (transferAmountField != null) transferAmountField.setText("");
        if (transferFrequencyField != null) transferFrequencyField.setText("");
        if (transferExecutionDayField != null) transferExecutionDayField.setText("");
        if (transferBicSwiftField != null) transferBicSwiftField.setText("");
        if (transferActiveUntilField != null) transferActiveUntilField.setText("");
        if (transferNameField != null) transferNameField.setText("");
        if (transferDescriptionArea != null) transferDescriptionArea.setText("");

        if (transferTypeCombo != null) {
            transferTypeCombo.setSelectedIndex(0); // Ενδοτραπεζική
        }

        // ================= PAYMENT =================
        if (paymentActiveUntilField != null) paymentActiveUntilField.setText("");
        if (paymentMaxAmountField != null) paymentMaxAmountField.setText("");
        if (paymentNameField != null) paymentNameField.setText("");
        if (paymentDescriptionArea != null) paymentDescriptionArea.setText("");
        if (paymentCodeField != null) paymentCodeField.setText("");

        // ================= MAIN TYPE =================
        if (typeCombo != null) {
            typeCombo.setSelectedIndex(0); // Μεταφορά
        }

        // ================= CARD RESET =================
        if (cardLayout != null && cardsPanel != null) {
            cardLayout.show(cardsPanel, TRANSFER_CARD);
        }
    }



    // ==================================================
    // ================= GETTERS ========================
    // ==================================================
    public JTextField getTransferIbanField() { return transferIbanField; }
    public JTextField getPaymentCodeField() { return paymentCodeField; }
    public JComboBox<String> getTransferTypeCombo() { return transferTypeCombo; }
    public JButton getCreateButton() { return createButton; }
    public JButton getBackButton() { return backButton; }

	public JTextField getTransferAmountField() {
		return transferAmountField;
	}

	public JTextField getTransferFrequencyField() {
		return transferFrequencyField;
	}

	public JTextField getTransferExecutionDayField() {
		return transferExecutionDayField;
	}

	public JTextField getTransferBicSwiftField() {
		return transferBicSwiftField;
	}

	public JTextField getTransferActiveUntilField() {
		return transferActiveUntilField;
	}

	public JTextField getTransferNameField() {
		return transferNameField;
	}

	public JTextArea getTransferDescriptionArea() {
		return transferDescriptionArea;
	}

	public JTextField getPaymentActiveUntilField() {
		return paymentActiveUntilField;
	}

	public JTextField getPaymentMaxAmountField() {
		return paymentMaxAmountField;
	}

	public JTextField getPaymentNameField() {
		return paymentNameField;
	}

	public JTextArea getPaymentDescriptionArea() {
		return paymentDescriptionArea;
	}

	public JPanel getCardsPanel() {
		return cardsPanel;
	}

	public CardLayout getCardLayout() {
		return cardLayout;
	}

	public JComboBox<String> getTypeCombo() {
		return typeCombo;
	}
    
    
}






