package recovida.idas.rl.gui.ui;

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

    public static final Icon ICON = UIManager.getIcon("OptionPane.errorIcon");
    public static final BufferedImage BUFFERED_IMAGE = new BufferedImage(
            ICON.getIconWidth(), ICON.getIconHeight(),
            BufferedImage.TYPE_INT_ARGB);

    public WarningIcon() {
        super();
        setText("!");
        setForeground(Color.RED);
        Graphics2D g = BUFFERED_IMAGE.createGraphics();
        ICON.paintIcon(null, g, 0, 0);
        g.dispose();
        setVisible(false);
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
        label.setIcon(new ImageIcon(BUFFERED_IMAGE.getScaledInstance(size, size,
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
