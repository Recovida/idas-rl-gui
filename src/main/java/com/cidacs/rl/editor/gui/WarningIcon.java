package com.cidacs.rl.editor.gui;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class WarningIcon extends JLabel {

    private static final long serialVersionUID = 5027707487801632406L;

    private boolean ready = false;

    private static final Icon icon = UIManager.getIcon("OptionPane.errorIcon");
    private static final BufferedImage bufferedImage = new BufferedImage(
            icon.getIconWidth(), icon.getIconHeight(),
            BufferedImage.TYPE_INT_ARGB);

    public WarningIcon() {
        super();
        setText("!");
        setForeground(Color.RED);
        Graphics2D g = bufferedImage.createGraphics();
        icon.paintIcon(null, g, 0, 0);
        g.dispose();
        setVisible(true);
    }

    @Override
    public void setVisible(boolean visible) {
        super.setVisible(visible);
        if (visible && !ready) {
            int d = getSize().height; // square
            if (d > 0) {
                applyIconToLabel(this, d);
                setText("");
                ready = true;
            } else // not ready yet
                SwingUtilities.invokeLater(() -> setVisible(visible));
        }
    }

    public static void applyIconToLabel(JLabel label, int size) {
        label.setIcon(new ImageIcon(bufferedImage.getScaledInstance(size, size,
                Image.SCALE_SMOOTH)));
    }

    public static void applyIconToLabel(JLabel label) {
        int d = label.getSize().height; // square
        if (d > 0) {
            applyIconToLabel(label);
        } else // not ready yet
            SwingUtilities.invokeLater(() -> applyIconToLabel(label));
    }

}
