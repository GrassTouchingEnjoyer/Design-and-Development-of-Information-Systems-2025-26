package view;
import javax.swing.*;
import java.awt.*;

public class CustomerDashboardView extends JPanel {

    private JLabel balanceLabel;
    private JList<String> transactionsList;
    private JPanel transactionsPanel;
    private JLabel customerNameLabel;

    
    private JButton depositButton;
    private JButton withdrawButton;
    private JButton transferButton;
    private JButton billsButton;
    private JButton changeAccountButton;
    private JButton standingOrderButton;
    private JButton createAccountButton;
    private JButton shareAccountButton;
    private JButton logoutButton;
    private JButton infoButton;
    private JButton blockAccountButton;


    public CustomerDashboardView() {
        setLayout(new BorderLayout(15, 15));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // ================= LEFT PANEL =================
        JPanel leftPanel = new JPanel(new GridLayout(3, 1, 10, 10));
        leftPanel.setBorder(BorderFactory.createTitledBorder("Μενού"));

        infoButton = new JButton("Πληροφορίες Λογαριασμού");
        standingOrderButton = new JButton("Πάγιες Εντολές");
        billsButton = new JButton("Λογαριασμοί Πληρωμής");

        leftPanel.add(infoButton);
        leftPanel.add(standingOrderButton);
        leftPanel.add(billsButton);

        // ================= CENTER PANEL =================
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setBorder(BorderFactory.createTitledBorder("Κύριος Λογαριασμός"));

     // Όνομα πελάτη
        customerNameLabel = new JLabel("Όνομα Πελάτη");
        customerNameLabel.setFont(new Font("Arial", Font.BOLD, 22));
        customerNameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Υπόλοιπο
        balanceLabel = new JLabel("Υπόλοιπο: 5.250,00 €");
        balanceLabel.setFont(new Font("Arial", Font.BOLD, 28));
        balanceLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel actionButtonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        depositButton = new JButton("Κατάθεση");
        withdrawButton = new JButton("Ανάληψη");
        transferButton = new JButton("Μεταφορά");

        actionButtonsPanel.add(depositButton);
        actionButtonsPanel.add(withdrawButton);
        actionButtonsPanel.add(transferButton);

        centerPanel.add(Box.createVerticalGlue());
        centerPanel.add(customerNameLabel);        // ΠΡΟΣΘΗΚΗ
        centerPanel.add(Box.createVerticalStrut(10));
        centerPanel.add(balanceLabel);
        centerPanel.add(Box.createVerticalStrut(20));
        centerPanel.add(actionButtonsPanel);
        centerPanel.add(Box.createVerticalGlue());

        // ================= RIGHT PANEL =================
        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.setBorder(BorderFactory.createTitledBorder("Συναλλαγές"));

        transactionsPanel = new JPanel();
        transactionsPanel.setLayout(new BoxLayout(transactionsPanel, BoxLayout.Y_AXIS));

        JScrollPane scrollPane = new JScrollPane(transactionsPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        rightPanel.add(scrollPane, BorderLayout.CENTER);


        // ================= BOTTOM PANEL =================
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 10));

        changeAccountButton = new JButton("Αλλαγή Λογαριασμού");
        blockAccountButton = new JButton("Φραγή Λογαριασμού");
        createAccountButton = new JButton("Δημιουργία Λογαριασμού");
        shareAccountButton = new JButton("Μοίρασμα Λογαριασμού");
        logoutButton = new JButton("Log out");
        logoutButton.setFont(new Font("Arial", Font.BOLD, 15));

        
        bottomPanel.add(logoutButton);
        bottomPanel.add(changeAccountButton);
        bottomPanel.add(blockAccountButton);
        bottomPanel.add(createAccountButton);
        bottomPanel.add(shareAccountButton);

        // ================= ADD TO MAIN =================
        add(leftPanel, BorderLayout.WEST);
        add(centerPanel, BorderLayout.CENTER);
        add(rightPanel, BorderLayout.EAST);
        add(bottomPanel, BorderLayout.SOUTH);
    }
    
    public void addTransaction(String text) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));

        JLabel label = new JLabel(text);
        label.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        label.setFont(new Font("Arial", Font.PLAIN, 10));

        panel.add(label, BorderLayout.CENTER);

        transactionsPanel.add(panel);
        transactionsPanel.add(Box.createVerticalStrut(5));

        transactionsPanel.revalidate();
        transactionsPanel.repaint();
    }

    public void clearTransactions() {
        transactionsPanel.removeAll();
        transactionsPanel.revalidate();
        transactionsPanel.repaint();
    }


    public JButton getDepositButton() {
		return depositButton;
	}

	public JButton getWithdrawButton() {
		return withdrawButton;
	}

	public JButton getTransferButton() {
		return transferButton;
	}
	
	public JButton getBillsButton() {
		return billsButton;
	}
	
	public JButton getChangeAccountButton() {
	    return changeAccountButton;
	}

	public JButton getStandingOrderButton() {
		return standingOrderButton;
	}

	public JButton getCreateAccountButton() {
		return createAccountButton;
	}

	public JButton getShareAccountButton() {
		return shareAccountButton;
	}
	
	public JButton getLogoutButton() {
	    return logoutButton;
	}
	
	public JLabel getCustomerNameLabel() {
	    return customerNameLabel;
	}

	public void setCustomerName(String name) {
	    customerNameLabel.setText(name);
	}

    public JLabel getBalanceLabel() {
        return balanceLabel;
    }

	public JButton getInfoButton() {
		return infoButton;
	}

	public JButton getBlockAccountButton() {
		return blockAccountButton;
	}
    
	
    
    
    
}

