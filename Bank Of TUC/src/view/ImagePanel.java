package view;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

class ImagePanel extends JPanel {
    private Image image;

    public ImagePanel(String path) {
        image = new ImageIcon(path).getImage();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (image != null) {
            int panelWidth = getWidth();
            int panelHeight = getHeight();

            int imgWidth = image.getWidth(this);
            int imgHeight = image.getHeight(this);

            // Keep aspect ratio
            double scale = Math.min(
                    (double) panelWidth / imgWidth,
                    (double) panelHeight / imgHeight
            );

            int newW = (int) (imgWidth * scale);
            int newH = (int) (imgHeight * scale);

            int x = (panelWidth - newW) / 2;
            int y = (panelHeight - newH) / 2;

            g.drawImage(image, x, y, newW, newH, this);
        }
    }
}

