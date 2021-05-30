package recovida.idas.rl.gui.ui.container;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;

import recovida.idas.rl.gui.lang.MessageProvider;
import recovida.idas.rl.gui.ui.JMenuWithBorder;

public class MainMenuBar extends JMenuBar {

    private static final long serialVersionUID = -8153106866054489899L;

    // File
    private JMenu fileMenu;
    private JMenuItem newFileMenuItem;
    private JMenuItem openFileMenuItem;
    private JMenuItem saveFileMenuItem;
    private JMenuItem saveAsFileMenuItem;
    private JMenuItem exitMenuItem;

    // Edit
    private JMenu editMenu;
    private JMenuItem undoMenuItem;
    private JMenuItem redoMenuItem;

    // Run
    private JMenu runMenu;
    private JMenuItem runMenuItem;

    // Help
    private JMenu helpMenu;
    private JMenuItem aboutMenuItem;

    // File name
    private JLabel currentFileLbl;

    public MainMenuBar() {
        fileMenu = new JMenuWithBorder("_File");
        add(fileMenu);

        newFileMenuItem = new JMenuItem("_New");
        fileMenu.add(newFileMenuItem);

        JSeparator separator_1 = new JSeparator();
        fileMenu.add(separator_1);

        openFileMenuItem = new JMenuItem("_Open...");
        fileMenu.add(openFileMenuItem);

        JSeparator separator_2 = new JSeparator();
        fileMenu.add(separator_2);

        saveFileMenuItem = new JMenuItem("_Save");
        fileMenu.add(saveFileMenuItem);

        saveAsFileMenuItem = new JMenuItem("_Save as...");
        fileMenu.add(saveAsFileMenuItem);

        JSeparator separator = new JSeparator();
        fileMenu.add(separator);

        exitMenuItem = new JMenuItem("_Exit");
        fileMenu.add(exitMenuItem);

        editMenu = new JMenuWithBorder("_Edit");
        add(editMenu);

        undoMenuItem = new JMenuItem("_Undo");
        undoMenuItem.setEnabled(false);
        editMenu.add(undoMenuItem);

        redoMenuItem = new JMenuItem("_Redo");
        redoMenuItem.setEnabled(false);
        editMenu.add(redoMenuItem);

        runMenu = new JMenuWithBorder("_Run");
        add(runMenu);

        runMenuItem = new JMenuItem("_Run using this configuration");
        runMenuItem.setEnabled(false);
        runMenu.add(runMenuItem);

        helpMenu = new JMenuWithBorder("_Help");
        add(helpMenu);

        aboutMenuItem = new JMenuItem("_About");
        helpMenu.add(aboutMenuItem);

        Component menuGlue = Box.createGlue();
        add(menuGlue);

        currentFileLbl = new JLabel("_unsaved file");
        Font f = currentFileLbl.getFont();
        currentFileLbl.setFont(f.deriveFont(Font.ITALIC).deriveFont(f.getSize2D() * 0.9f));
        currentFileLbl.setHorizontalAlignment(SwingConstants.TRAILING);
        add(currentFileLbl);

        Component currentFileSpacer = Box.createRigidArea(new Dimension(20, 20));
        add(currentFileSpacer);
    }

    public void updateLocalisedStrings() {
        // file
        fileMenu.setText(MessageProvider.getMessage("menu.file"));
        newFileMenuItem.setText(MessageProvider.getMessage("menu.file.new"));
        openFileMenuItem.setText(MessageProvider.getMessage("menu.file.open"));
        saveFileMenuItem.setText(MessageProvider.getMessage("menu.file.save"));
        saveAsFileMenuItem.setText(MessageProvider.getMessage("menu.file.saveas"));
        exitMenuItem.setText(MessageProvider.getMessage("menu.file.exit"));

        // edit
        editMenu.setText(MessageProvider.getMessage("menu.edit"));

        // run
        runMenu.setText(MessageProvider.getMessage("menu.run"));
        runMenuItem.setText(MessageProvider.getMessage("menu.run.run"));

        // help
        helpMenu.setText(MessageProvider.getMessage("menu.help"));
        aboutMenuItem.setText(MessageProvider.getMessage("menu.help.about"));

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

    public JMenu getHelpMenu() {
        return helpMenu;
    }

    public JMenuItem getAboutMenuItem() {
        return aboutMenuItem;
    }

    public JLabel getCurrentFileLbl() {
        return currentFileLbl;
    }

}
