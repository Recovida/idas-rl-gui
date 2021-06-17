package recovida.idas.rl.gui.ui.container;

import java.awt.Component;

import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JSeparator;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;

import recovida.idas.rl.gui.ExecutionStatus;
import recovida.idas.rl.gui.lang.MessageProvider;
import recovida.idas.rl.gui.ui.LanguageComboBox;
import recovida.idas.rl.gui.ui.Translatable;

/**
 * A toolbar for the main window.
 */
public class MainToolBar extends JToolBar implements Translatable {

    private ExecutionStatus execStatus = ExecutionStatus.UNSAVED;

    private JButton newFileBtn;

    private JButton openFileBtn;

    private JButton saveFileBtn;

    private JSeparator sep1;

    private JButton undoBtn;

    private JButton redoBtn;

    private JSeparator sep2;

    private JButton runBtn;

    /**
     * Creates an instance.
     */
    public MainToolBar() {

        newFileBtn = new JButton("");
        newFileBtn.setIcon(new ImageIcon(MainToolBar.class
                .getResource("/toolbarButtonGraphics/general/New24.gif")));
        add(newFileBtn);

        openFileBtn = new JButton("");
        openFileBtn.setIcon(new ImageIcon(MainToolBar.class
                .getResource("/toolbarButtonGraphics/general/Open24.gif")));
        add(openFileBtn);

        saveFileBtn = new JButton("");
        saveFileBtn.setIcon(new ImageIcon(MainToolBar.class
                .getResource("/toolbarButtonGraphics/general/Save24.gif")));
        add(saveFileBtn);

        sep1 = new JSeparator();
        sep1.setOrientation(SwingConstants.VERTICAL);
        add(sep1);

        undoBtn = new JButton("");
        undoBtn.setEnabled(false);
        undoBtn.setIcon(new ImageIcon(MainToolBar.class
                .getResource("/toolbarButtonGraphics/general/Undo24.gif")));
        add(undoBtn);

        redoBtn = new JButton("");
        redoBtn.setEnabled(false);
        redoBtn.setIcon(new ImageIcon(MainToolBar.class
                .getResource("/toolbarButtonGraphics/general/Redo24.gif")));
        add(redoBtn);

        sep2 = new JSeparator();
        sep2.setOrientation(SwingConstants.VERTICAL);
        add(sep2);

        runBtn = new JButton("");
        runBtn.setEnabled(false);
        runBtn.setIcon(new ImageIcon(MainToolBar.class
                .getResource("/toolbarButtonGraphics/media/Play24.gif")));
        add(runBtn);

        setFloatable(false);

        cancelBtn = new JButton("");
        cancelBtn.setIcon(new ImageIcon(MainToolBar.class
                .getResource("/toolbarButtonGraphics/media/Stop24.gif")));
        cancelBtn.setEnabled(false);
        add(cancelBtn);

        separator = new JSeparator();
        separator.setOrientation(SwingConstants.VERTICAL);
        add(separator);

        horizontalGlue = Box.createHorizontalGlue();
        add(horizontalGlue);

        languageCbox = new LanguageComboBox();
        add(languageCbox);

    }

    private static final long serialVersionUID = 5509499175879481354L;

    private final Component horizontalGlue;

    private final JSeparator separator;

    private final LanguageComboBox languageCbox;

    private final JButton cancelBtn;

    public JButton getNewFileBtn() {
        return newFileBtn;
    }

    public JButton getOpenFileBtn() {
        return openFileBtn;
    }

    public JButton getSaveFileBtn() {
        return saveFileBtn;
    }

    public JButton getUndoBtn() {
        return undoBtn;
    }

    public JButton getRedoBtn() {
        return redoBtn;
    }

    public JButton getRunBtn() {
        return runBtn;
    }

    public JButton getCancelBtn() {
        return cancelBtn;
    }

    public LanguageComboBox getLanguageCBox() {
        return languageCbox;
    }

    @Override
    public void updateLocalisedStrings() {
        newFileBtn.setToolTipText(MessageProvider.getMessage("menu.file.new"));
        openFileBtn
                .setToolTipText(MessageProvider.getMessage("menu.file.open"));
        saveFileBtn
                .setToolTipText(MessageProvider.getMessage("menu.file.save"));
        runBtn.setToolTipText(
                MessageProvider.getMessage("menu.run.run" + execStatus.sfx()));
        cancelBtn.setToolTipText(MessageProvider.getMessage("menu.run.cancel"));
    }

    public ExecutionStatus getExecStatus() {
        return execStatus;
    }

    public void setExecStatus(ExecutionStatus execStatus) {
        if (execStatus != null) {
            this.execStatus = execStatus;
            updateLocalisedStrings();
            runBtn.setEnabled(execStatus == ExecutionStatus.READY);
            cancelBtn.setEnabled(execStatus == ExecutionStatus.RUNNING);
            languageCbox.setEnabled(execStatus != ExecutionStatus.RUNNING);
        }
    }
}
