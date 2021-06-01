package recovida.idas.rl.gui.ui.container;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.util.HashSet;
import java.util.OptionalInt;
import java.util.Set;

import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JSeparator;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;

import recovida.idas.rl.gui.lang.MessageProvider;
import recovida.idas.rl.gui.ui.JMenuWithBorder;
import recovida.idas.rl.gui.ui.Translatable;

/**
 * The menu bar for the main window.
 */
public class MainMenuBar extends JMenuBar implements Translatable {

    private static final long serialVersionUID = -8153106866054489899L;

    // File

    private final JMenu fileMenu;

    private final JMenuItem newFileMenuItem;

    private final JMenuItem openFileMenuItem;

    private final JMenuItem saveFileMenuItem;

    private final JMenuItem saveAsFileMenuItem;

    private final JMenuItem exitMenuItem;

    // Edit

    private final JMenu editMenu;

    private final JMenuItem undoMenuItem;

    private final JMenuItem redoMenuItem;

    // Run

    private final JMenu runMenu;

    private final JMenuItem runMenuItem;

    // Help

    private final JMenu helpMenu;

    private final JMenuItem aboutMenuItem;

    // File name

    private final JLabel currentFileLbl;

    private final JMenuItem cancelMenuItem;

    /**
     * Creates an instance.
     */
    public MainMenuBar() {
        fileMenu = new JMenuWithBorder("_File");
        add(fileMenu);

        newFileMenuItem = new JMenuItem("_New");
        newFileMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N,
                InputEvent.CTRL_DOWN_MASK));
        newFileMenuItem.setIcon(new ImageIcon(MainMenuBar.class
                .getResource("/toolbarButtonGraphics/general/New16.gif")));
        fileMenu.add(newFileMenuItem);

        JSeparator sep1 = new JSeparator();
        fileMenu.add(sep1);

        openFileMenuItem = new JMenuItem("_Open...");
        openFileMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O,
                InputEvent.CTRL_DOWN_MASK));
        openFileMenuItem.setIcon(new ImageIcon(MainMenuBar.class
                .getResource("/toolbarButtonGraphics/general/Open16.gif")));
        fileMenu.add(openFileMenuItem);

        JSeparator sep2 = new JSeparator();
        fileMenu.add(sep2);

        saveFileMenuItem = new JMenuItem("_Save");
        saveFileMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,
                InputEvent.CTRL_DOWN_MASK));
        saveFileMenuItem.setIcon(new ImageIcon(MainMenuBar.class
                .getResource("/toolbarButtonGraphics/general/Save16.gif")));
        fileMenu.add(saveFileMenuItem);

        saveAsFileMenuItem = new JMenuItem("_Save as...");
        saveAsFileMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,
                InputEvent.CTRL_DOWN_MASK | InputEvent.SHIFT_DOWN_MASK));
        saveAsFileMenuItem.setIcon(new ImageIcon(MainMenuBar.class
                .getResource("/toolbarButtonGraphics/general/SaveAs16.gif")));
        fileMenu.add(saveAsFileMenuItem);

        JSeparator separator = new JSeparator();
        fileMenu.add(separator);

        exitMenuItem = new JMenuItem("_Quit");
        exitMenuItem.setIcon(new ImageIcon(MainMenuBar.class
                .getResource("/toolbarButtonGraphics/navigation/Back16.gif")));
        exitMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q,
                InputEvent.CTRL_DOWN_MASK));
        fileMenu.add(exitMenuItem);

        editMenu = new JMenuWithBorder("_Edit");
        add(editMenu);

        undoMenuItem = new JMenuItem("_Undo");
        undoMenuItem.setIcon(new ImageIcon(MainMenuBar.class
                .getResource("/toolbarButtonGraphics/general/Undo16.gif")));
        undoMenuItem.setEnabled(false);
        undoMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z,
                InputEvent.CTRL_DOWN_MASK));
        editMenu.add(undoMenuItem);

        redoMenuItem = new JMenuItem("_Redo");
        redoMenuItem.setIcon(new ImageIcon(MainMenuBar.class
                .getResource("/toolbarButtonGraphics/general/Redo16.gif")));
        redoMenuItem.setEnabled(false);
        redoMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Y,
                InputEvent.CTRL_DOWN_MASK));
        editMenu.add(redoMenuItem);

        runMenu = new JMenuWithBorder("_Run");
        add(runMenu);

        runMenuItem = new JMenuItem("_Run using this configuration");
        runMenuItem.setIcon(new ImageIcon(MainMenuBar.class
                .getResource("/toolbarButtonGraphics/media/Play16.gif")));
        runMenuItem.setEnabled(false);
        runMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R,
                InputEvent.CTRL_DOWN_MASK));
        runMenu.add(runMenuItem);

        cancelMenuItem = new JMenuItem("_Cancel execution");
        getCancelMenuItem().setAccelerator(
                KeyStroke.getKeyStroke(KeyEvent.VK_H, InputEvent.CTRL_MASK));
        runMenu.add(getCancelMenuItem());
        getCancelMenuItem().setEnabled(false);

        helpMenu = new JMenuWithBorder("_Help");
        add(helpMenu);

        aboutMenuItem = new JMenuItem("_About");
        aboutMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F1,
                InputEvent.CTRL_DOWN_MASK));
        aboutMenuItem.setIcon(new ImageIcon(MainMenuBar.class
                .getResource("/toolbarButtonGraphics/general/About16.gif")));
        helpMenu.add(aboutMenuItem);

        Component menuGlue = Box.createGlue();
        add(menuGlue);

        currentFileLbl = new JLabel("_unsaved file");
        Font f = currentFileLbl.getFont();
        currentFileLbl.setFont(
                f.deriveFont(Font.ITALIC).deriveFont(f.getSize2D() * 0.9f));
        currentFileLbl.setHorizontalAlignment(SwingConstants.TRAILING);
        add(currentFileLbl);

        Component currentFileSpacer = Box
                .createRigidArea(new Dimension(20, 20));
        add(currentFileSpacer);

        setVisible(true);
    }

    @Override
    public void updateLocalisedStrings() {
        // file
        fileMenu.setText(MessageProvider.getMessage("menu.file"));
        newFileMenuItem.setText(MessageProvider.getMessage("menu.file.new"));
        openFileMenuItem.setText(MessageProvider.getMessage("menu.file.open"));
        saveFileMenuItem.setText(MessageProvider.getMessage("menu.file.save"));
        saveAsFileMenuItem
                .setText(MessageProvider.getMessage("menu.file.saveas"));
        exitMenuItem.setText(MessageProvider.getMessage("menu.file.exit"));

        // edit
        editMenu.setText(MessageProvider.getMessage("menu.edit"));

        // run
        runMenu.setText(MessageProvider.getMessage("menu.run"));
        runMenuItem.setText(MessageProvider.getMessage("menu.run.run"));
        getCancelMenuItem()
                .setText(MessageProvider.getMessage("menu.run.cancel"));

        // help
        helpMenu.setText(MessageProvider.getMessage("menu.help"));
        aboutMenuItem.setText(MessageProvider.getMessage("menu.help.about"));

        // mnemonics
        fillShortcuts(getComponents());
    }

    protected static void fillShortcuts(Component[] menu) {
        Set<Integer> used = new HashSet<>();
        for (Component m : menu) {
            if (m instanceof JMenuItem) { // includes JMenu
                if (m instanceof JMenu)
                    fillShortcuts(((JMenu) m).getMenuComponents());
                OptionalInt mnemonic = ((JMenuItem) m).getText().chars()
                        .filter(c -> !used.contains(c)).findFirst();
                if (mnemonic.isPresent()) {
                    ((JMenuItem) m).setMnemonic(mnemonic.getAsInt());
                    used.add(mnemonic.getAsInt());
                }
            }
        }
    }

    public JMenu getFileMenu() {
        return fileMenu;
    }

    public JMenuItem getNewFileMenuItem() {
        return newFileMenuItem;
    }

    public JMenuItem getOpenFileMenuItem() {
        return openFileMenuItem;
    }

    public JMenuItem getSaveFileMenuItem() {
        return saveFileMenuItem;
    }

    public JMenuItem getSaveAsFileMenuItem() {
        return saveAsFileMenuItem;
    }

    public JMenuItem getExitMenuItem() {
        return exitMenuItem;
    }

    public JMenu getEditMenu() {
        return editMenu;
    }

    public JMenuItem getUndoMenuItem() {
        return undoMenuItem;
    }

    public JMenuItem getRedoMenuItem() {
        return redoMenuItem;
    }

    public JMenu getRunMenu() {
        return runMenu;
    }

    public JMenuItem getRunMenuItem() {
        return runMenuItem;
    }

    @Override
    public JMenu getHelpMenu() {
        return helpMenu;
    }

    public JMenuItem getAboutMenuItem() {
        return aboutMenuItem;
    }

    public JLabel getCurrentFileLbl() {
        return currentFileLbl;
    }

    public JMenuItem getCancelMenuItem() {
        return cancelMenuItem;
    }

}
