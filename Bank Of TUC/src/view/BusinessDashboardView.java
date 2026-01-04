package view;

import javax.swing.*;
import java.awt.*;

public class BusinessDashboardView extends JPanel {

    private JButton accountInfoButton;
    private JButton billsButton;
    //private JButton standingOrdersButton;
    private JButton blockAccountButton;
    private JButton logoutButton;
    private JLabel customerNameLabel;


    private JLabel balanceLabel;
    private JPanel transactionsPanel;

    public BusinessDashboardView() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        Font buttonFont = new Font("Arial", Font.BOLD, 16);
        Font balanceFont = new Font("Arial", Font.BOLD, 26);

        // -------- LEFT PANEL --------
        JPanel leftPanel = new JPanel(new GridLayout(3, 1, 10, 10));
        accountInfoButton = new JButton("Πληροφορίες Λογαριασμού");
        billsButton = new JButton("Λογαριασμοί Πληρωμής");
        //standingOrdersButton = new JButton("Πάγιες Εντολές");

        accountInfoButton.setFont(buttonFont);
        billsButton.setFont(buttonFont);
        //standingOrdersButton.setFont(buttonFont);

        leftPanel.add(accountInfoButton);
        leftPanel.add(billsButton);
        // leftPanel.add(standingOrdersButton);

        // -------- CENTER PANEL --------
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

        centerPanel.add(Box.createVerticalGlue());
        centerPanel.add(customerNameLabel);        // ΠΡΟΣΘΗΚΗ
        centerPanel.add(Box.createVerticalStrut(10));
        centerPanel.add(balanceLabel);
        centerPanel.add(Box.createVerticalGlue());

        // -------- RIGHT PANEL --------
        transactionsPanel = new JPanel();
        transactionsPanel.setLayout(new BoxLayout(transactionsPanel, BoxLayout.Y_AXIS));
        JScrollPane scrollPane = new JScrollPane(transactionsPanel);

        // -------- BOTTOM PANEL --------
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        blockAccountButton = new JButton("Φραγή Λογαριασμού");
        blockAccountButton.setFont(buttonFont);
        logoutButton = new JButton("Log out");
        logoutButton.setFont(new Font("Arial", Font.BOLD, 15));
        
        bottomPanel.add(logoutButton);
        bottomPanel.add(blockAccountButton);

        add(leftPanel, BorderLayout.WEST);
        add(centerPanel, BorderLayout.CENTER);
        add(scrollPane, BorderLayout.EAST);
        add(bottomPanel, BorderLayout.SOUTH);
    }
    
    public void addTransaction(String text) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));

        JLabel label = new JLabel(text);
        label.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

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



    // -------- GETTERS --------
    public JButton getBillsButton() { return billsButton; }
    //public JButton getStandingOrdersButton() { return standingOrdersButton; }
    public JButton getBlockAccountButton() { return blockAccountButton; }
    
    public JButton getLogoutButton() {
        return logoutButton;
    }
	public JButton getAccountInfoButton() {
		return accountInfoButton;
	}
	public JLabel getBalanceLabel() {
		return balanceLabel;
	}
	public JPanel getTransactionsPanel() {
		return transactionsPanel;
	}
	public JLabel getCustomerNameLabel() {
		return customerNameLabel;
	}


	public void setCustomerName(String name) {
	    customerNameLabel.setText(name);
	}
	
	
    
    
}

