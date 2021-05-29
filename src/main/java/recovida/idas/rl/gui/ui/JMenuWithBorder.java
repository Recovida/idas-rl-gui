package recovida.idas.rl.gui.ui;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.JMenu;

public class JMenuWithBorder extends JMenu {

    private static final long serialVersionUID = -3113821909735355158L;

    public JMenuWithBorder() {
        super();
        init();
    }

    public JMenuWithBorder(String s) {
        super(s);
        init();
    }

    public JMenuWithBorder(Action a) {
        super(a);
        init();
    }

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
