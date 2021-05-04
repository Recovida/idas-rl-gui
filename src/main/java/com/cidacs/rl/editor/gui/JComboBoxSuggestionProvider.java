package com.cidacs.rl.editor.gui;

import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.text.Normalizer;
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

    final static Pattern ALPHANUMERIC = Pattern.compile("[^A-Za-z0-9]");

    public JComboBoxSuggestionProvider(JComboBox<String> field) {
        this.field = field;
        this.menu = new JPopupMenu();

        menu.setFocusable(false);
        JTextField tf = ((JTextField) this.field.getEditor()
                .getEditorComponent());
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
                    System.out.println(idx);
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
            if (menu.isVisible()) {
                SwingUtilities.convertPointToScreen(p, field);
                menu.setLocation(p.x + dx, p.y + dy);
            } else {
                menu.show(field, p.x + dx, p.y + dy);
            }
        }
    }

    protected void fillMenu(String value) {
        menu.removeAll();
        for (int i = 0; menu.getComponentCount() < getLimit()
                && i < field.getItemCount(); i++) {
            String v = field.getItemAt(i);
            if (clean(v).contains(clean(value))) {
                JMenuItem item = new JMenuItem(v);
                item.addActionListener(e -> {
                    field.setSelectedItem(v);
                });
                menu.add(item);
            }
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
