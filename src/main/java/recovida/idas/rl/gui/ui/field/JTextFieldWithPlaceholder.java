package recovida.idas.rl.gui.ui.field;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class JTextFieldWithPlaceholder extends JTextField
        implements FieldWithPlaceholder {

    private static final long serialVersionUID = 6919614017717410064L;

    protected String placeholder = "";

    protected int verticalPlaceholderOffset = 0;

    public int getVerticalPlaceholderOffset() {
        return verticalPlaceholderOffset;
    }

    public void setVerticalPlaceholderOffset(int verticalPlaceholderOffset) {
        this.verticalPlaceholderOffset = verticalPlaceholderOffset;
    }

    @Override
    public String getPlaceholder() {
        return placeholder;
    }

    @Override
    public void setPlaceholder(String placeholder) {
        this.placeholder = placeholder;
        SwingUtilities.invokeLater(() -> repaint());
    }

    @Override
    protected void paintComponent(final Graphics pG) {
        super.paintComponent(pG);

        if (placeholder != null && placeholder.length() > 0
                && getText().length() == 0) {
            ((Graphics2D) pG).setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);
            ((Graphics2D) pG).setColor(getDisabledTextColor());
            ((Graphics2D) pG).drawString(placeholder, getInsets().left,
                    pG.getFontMetrics().getMaxAscent() + getInsets().top
                            + verticalPlaceholderOffset);
        }
    }

}
