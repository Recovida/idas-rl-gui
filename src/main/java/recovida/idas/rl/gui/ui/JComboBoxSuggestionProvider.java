package recovida.idas.rl.gui.ui;

import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.text.Normalizer;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Pattern;

import javax.swing.JComboBox;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class JComboBoxSuggestionProvider {

    private JComboBox<String> field;

    private JPopupMenu menu;

    private int limit = 20;

    static final Pattern ALPHANUMERIC = Pattern.compile("[^A-Za-z0-9]");

    public JComboBoxSuggestionProvider(JComboBox<String> field) {
        this.field = field;
        this.menu = new JPopupMenu();

        menu.setFocusable(false);
        JTextField tf = (JTextField) this.field.getEditor()
                .getEditorComponent();
        tf.addKeyListener(new KeyListener() {

            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
                int c = e.getKeyCode();
                if (menu.isVisible() && isVerticalArrow(c)) {
                    e.consume();
                    int idx = (menu.getSelectionModel().getSelectedIndex() + 1)
                            % menu.getComponentCount();
                    menu.setSelected(menu.getComponent(idx));
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                int c = e.getKeyCode();
                if (!isModifier(c) && !isVerticalArrow(c)
                        && c != KeyEvent.VK_ENTER)
                    trigger(tf);
            }
        });

    }

    protected static boolean isVerticalArrow(int c) {
        return c == KeyEvent.VK_DOWN || c == KeyEvent.VK_UP
                || c == KeyEvent.VK_KP_DOWN || c == KeyEvent.VK_KP_UP;
    }

    protected static boolean isModifier(int c) {
        return c == KeyEvent.VK_CONTROL || c == KeyEvent.VK_SHIFT
                || c == KeyEvent.VK_ALT || c == KeyEvent.VK_ALT_GRAPH;
    }

    protected void trigger(JTextField tf) {
        Point p = tf.getCaret().getMagicCaretPosition();
        if (p == null || p.x > tf.getWidth() || p.y > tf.getHeight())
            return;
        int dx = 10, dy = 20;
        String text = tf.getText();
        if (text == null || text.isEmpty()) {
            menu.setVisible(false);
        } else {
            fillMenu(text);
            if (menu.getComponentCount() > 0) {
                if (menu.isVisible()) {
                    SwingUtilities.convertPointToScreen(p, field);
                    menu.setLocation(p.x + dx, p.y + dy);
                } else {
                    menu.show(field, p.x + dx, p.y + dy);
                }
            } else
                menu.setVisible(false);
        }
    }

    protected void fillMenu(String value) {
        menu.removeAll();
        List<String> list1 = new LinkedList<>();
        List<String> list2 = new LinkedList<>();
        for (int i = 0; list1.size() < getLimit() && list2.size() < getLimit()
                && i < field.getItemCount(); i++) {
            String v = field.getItemAt(i);
            String cleanV = clean(v);
            String cleanValue = clean(value);
            if (cleanV.contains(cleanValue)) {
                if (cleanV.startsWith(cleanValue))
                    list1.add(v);
                else
                    list2.add(v);
            }
        }
        list1.addAll(list2);
        if (list1.size() == 1 && list1.get(0).equals(value))
            return;
        for (String v : list1) {
            JMenuItem item = new JMenuItem(v);
            item.addActionListener(e -> {
                field.setSelectedIndex(-1);
                field.setSelectedItem(v);
            });
            menu.add(item);
            if (menu.getComponentCount() > getLimit())
                break;
        }
    }

    protected static String clean(String input) {
        if (input == null)
            return "";
        return ALPHANUMERIC
                .matcher(new StringBuilder(
                        Normalizer.normalize(input, Normalizer.Form.NFD)))
                .replaceAll("").toLowerCase();
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

}
