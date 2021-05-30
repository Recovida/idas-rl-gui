package recovida.idas.rl.gui.ui.container;

import javax.swing.JToolBar;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;

import recovida.idas.rl.gui.lang.MessageProvider;

public class MainToolbar extends JToolBar {
    
    protected JButton newFileBtn;
    protected JButton openFileBtn;
    protected JButton saveFileBtn;
    protected JSeparator sep1;
    protected JButton undoBtn;
    protected JButton redoBtn;
    protected JSeparator sep2;
    protected JButton runBtn;
    
    
    public MainToolbar() {
        
        newFileBtn = new JButton("");
        newFileBtn.setIcon(new ImageIcon(MainToolbar.class.getResource("/toolbarButtonGraphics/general/New24.gif")));
        add(newFileBtn);
        
        openFileBtn = new JButton("");
        openFileBtn.setIcon(new ImageIcon(MainToolbar.class.getResource("/toolbarButtonGraphics/general/Open24.gif")));
        add(openFileBtn);
        
        saveFileBtn = new JButton("");
        saveFileBtn.setIcon(new ImageIcon(MainToolbar.class.getResource("/toolbarButtonGraphics/general/Save24.gif")));
        add(saveFileBtn);
        
        sep1 = new JSeparator();
        sep1.setOrientation(SwingConstants.VERTICAL);
        add(sep1);
        
        undoBtn = new JButton("");
        undoBtn.setEnabled(false);
        undoBtn.setIcon(new ImageIcon(MainToolbar.class.getResource("/toolbarButtonGraphics/general/Undo24.gif")));
        add(undoBtn);
        
        redoBtn = new JButton("");
        redoBtn.setEnabled(false);
        redoBtn.setIcon(new ImageIcon(MainToolbar.class.getResource("/toolbarButtonGraphics/general/Redo24.gif")));
        add(redoBtn);
        
        sep2 = new JSeparator();
        sep2.setOrientation(SwingConstants.VERTICAL);
        add(sep2);
        
        runBtn = new JButton("");
        runBtn.setEnabled(false);
        runBtn.setIcon(new ImageIcon(MainToolbar.class.getResource("/toolbarButtonGraphics/media/Play24.gif")));
        add(runBtn);
        
        setFloatable(false);
    }

    private static final long serialVersionUID = 5509499175879481354L;


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
    
    public void updateLocalisedStrings() {
        newFileBtn.setToolTipText(MessageProvider.getMessage("menu.file.new"));
        openFileBtn.setToolTipText(MessageProvider.getMessage("menu.file.open"));
        saveFileBtn.setToolTipText(MessageProvider.getMessage("menu.file.save"));
        runBtn.setToolTipText(MessageProvider.getMessage("menu.run.run"));
    }
}
