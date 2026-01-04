package view;
import javax.swing.*;
import java.awt.*;

public class StartView extends JPanel {

    private JList<String> roleList;
    private JButton continueButton;

    public StartView() {
        setLayout(new BorderLayout(20, 20));
        setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));

        JLabel titleLabel = new JLabel("Επιλέξτε τύπο χρήστη", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 26));

        String[] roles = {
                "Πελάτης - Φυσικό Πρόσωπο",
                "Πελάτης - Επιχείρηση",
                "Προσωπικό Τράπεζας",
                "Διαχειριστής Τράπεζας",
                "Administrator"
        };

        roleList = new JList<>(roles);
        roleList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        roleList.setFont(new Font("Arial", Font.PLAIN, 20));
        roleList.setFixedCellHeight(40); 
        roleList.setSelectedIndex(0);

        JScrollPane scrollPane = new JScrollPane(roleList);

        continueButton = new JButton("Συνέχεια");
        continueButton.setFont(new Font("Arial", Font.BOLD, 20));
        continueButton.setPreferredSize(new Dimension(200, 50));

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(continueButton);

        add(titleLabel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    // Getters για Controller
    public JList<String> getRoleList() {
        return roleList;
    }

    public JButton getContinueButton() {
        return continueButton;
    }
}

