package recovida.idas.rl.gui.ui;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.JMenu;

/**
 * A custom {@link JMenu} with a tiny border and minimum width.
 */
public class JMenuWithBorder extends JMenu {

    private static final long serialVersionUID = -3113821909735355158L;

    /**
     * Creates an instance.
     */
    public JMenuWithBorder() {
        super();
        init();
    }

    /**
     * Creates an instance.
     *
     * @param s the display text
     */
    public JMenuWithBorder(String s) {
        super(s);
        init();
    }

    /**
     * Creates an instance.
     *
     * @param a the action associated with the menu
     */
    public JMenuWithBorder(Action a) {
        super(a);
        init();
    }

    /**
     * Creates an instance.
     *
     * @param s the display text
     * @param b (not used)
     */
    public JMenuWithBorder(String s, boolean b) {
        super(s, b);
        init();
    }

    protected void init() {
        setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0, 30)));
        setBorderPainted(true);
        setHorizontalTextPosition(CENTER);
    }

    @Override
    public Dimension getPreferredSize() {
        Dimension d = super.getPreferredSize();
        d.width = Math.max(d.width, 60);
        return d;
    }

}
