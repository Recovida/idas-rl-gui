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

/**
 * A label that displays an error icon.
 */
public class ErrorIconLabel extends JLabel {

    private static final long serialVersionUID = 5027707487801632406L;

    private boolean ready = false;

    /**
     * The error icon.
     */
    public static final Icon ICON = UIManager.getIcon("OptionPane.errorIcon");

    /**
     * A buffered image containing the icon.
     */
    public static final BufferedImage BUFFERED_IMAGE = new BufferedImage(
            ICON.getIconWidth(), ICON.getIconHeight(),
            BufferedImage.TYPE_INT_ARGB);

    /**
     * Creates an instance of the label with the icon.
     */
    public ErrorIconLabel() {
        super();
        setText("!");
        setForeground(Color.RED);
        Graphics2D g = BUFFERED_IMAGE.createGraphics();
        ICON.paintIcon(null, g, 0, 0);
        g.dispose();
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

    /**
     * Adds an error icon to a label, with the given size.
     *
     * @param label the label to receive the error icon
     * @param size  the size of the square icon
     */
    public static void applyIconToLabel(JLabel label, int size) {
        label.setIcon(new ImageIcon(BUFFERED_IMAGE.getScaledInstance(size, size,
                Image.SCALE_SMOOTH)));
    }

    /**
     * Adds an error icon to a label, respecting its height.
     *
     * @param label the label to receive the error icon
     */
    public static void applyIconToLabel(JLabel label) {
        int d = label.getSize().height; // square
        if (d > 0) {
            applyIconToLabel(label);
        } else // not ready yet
            SwingUtilities.invokeLater(() -> applyIconToLabel(label));
    }

}
