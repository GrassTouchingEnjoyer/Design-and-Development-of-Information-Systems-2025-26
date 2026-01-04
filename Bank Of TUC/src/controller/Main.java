package controller;
import javax.swing.*;

import view.MainFrame;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainFrame frame = new MainFrame();

            new MainController(
                    frame,
                    frame.getStartView(),
                    frame.getLoginView()
            );

            frame.setVisible(true);
        });
    }
}

