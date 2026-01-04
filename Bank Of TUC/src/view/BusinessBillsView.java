package view;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class BusinessBillsView extends JPanel {

    private JPanel billsContainer;
    private JScrollPane scrollPane;
    private JButton backButton;
    private JButton issueButton;

    public BusinessBillsView() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel titleLabel = new JLabel("Λογαριασμοί Πληρωμής");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        billsContainer = new JPanel();
        billsContainer.setLayout(new BoxLayout(billsContainer, BoxLayout.Y_AXIS));
        billsContainer.setAlignmentY(Component.TOP_ALIGNMENT);

        scrollPane = new JScrollPane(billsContainer);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        // Bottom panel με κουμπί Πίσω
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        backButton = new JButton("Πίσω");
        issueButton = new JButton("Έκδοση λογαριασμού");
        bottomPanel.add(backButton);
        bottomPanel.add(issueButton);

        add(titleLabel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    public void clearBills() {
        billsContainer.removeAll();
        billsContainer.revalidate();
        billsContainer.repaint();
    }

    public void addBill(String description) {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));

        // Βάζουμε ένα προτεινόμενο μέγεθος για να φαίνεται
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60));
        panel.setMinimumSize(new Dimension(400, 60));
        panel.setPreferredSize(new Dimension(400, 60));

        JLabel label = new JLabel(description);
        label.setFont(new Font("Arial", Font.PLAIN, 16));
        label.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0)); // padding αριστερά

        panel.add(label, BorderLayout.CENTER);
        

        billsContainer.add(panel);
        billsContainer.add(Box.createVerticalStrut(5)); // λίγο κενό ανάμεσα

        billsContainer.revalidate();
        billsContainer.repaint();
    }
    
    public JButton getBackButton() {
        return backButton;
    }
    
    public JButton getIssueButton() {
        return issueButton;
    }
}
