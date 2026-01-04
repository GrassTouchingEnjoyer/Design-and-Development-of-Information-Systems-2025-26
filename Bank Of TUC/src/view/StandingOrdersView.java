package view;

import javax.swing.*;
import java.awt.*;

public class StandingOrdersView extends JPanel {

    private JPanel ordersContainer;
    private JButton backButton;
    private JButton createButton;

    public StandingOrdersView() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel titleLabel = new JLabel("Πάγιες Εντολές", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));

        ordersContainer = new JPanel();
        ordersContainer.setLayout(new BoxLayout(ordersContainer, BoxLayout.Y_AXIS));
        ordersContainer.setAlignmentY(Component.TOP_ALIGNMENT);

        JScrollPane scrollPane = new JScrollPane(ordersContainer);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        // Bottom panel
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 10));
        backButton = new JButton("Πίσω");
        createButton = new JButton("Δημιουργία");

        bottomPanel.add(backButton);
        bottomPanel.add(createButton);

        add(titleLabel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    // ---------- API για Controller ----------

    public void clearOrders() {
        ordersContainer.removeAll();
        ordersContainer.revalidate();
        ordersContainer.repaint();
    }

    public void addStandingOrder(String text, Runnable onDelete) {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60));

        JLabel label = new JLabel(text);
        label.setFont(new Font("Arial", Font.PLAIN, 16));
        label.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));

        JButton deleteButton = new JButton("Διαγραφή");
        deleteButton.addActionListener(e -> onDelete.run());

        panel.add(label, BorderLayout.CENTER);
        panel.add(deleteButton, BorderLayout.EAST);

        ordersContainer.add(panel);
        ordersContainer.add(Box.createVerticalStrut(5));

        ordersContainer.revalidate();
        ordersContainer.repaint();
    }

    public JButton getBackButton() {
        return backButton;
    }

    public JButton getCreateButton() {
        return createButton;
    }
}

