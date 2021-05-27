package recovida.idas.rl.gui.ui.container;

import java.awt.Component;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

import recovida.idas.rl.gui.lang.MessageProvider;

public class LinkageColumnButtonPanel extends JPanel {

    private static final long serialVersionUID = 3447357352169636148L;
    private JButton addPairBtn;
    private JButton deletePairBtn;
    private JButton addCopyColsBtn;

    public JButton getAddPairBtn() {
        return addPairBtn;
    }

    public JButton getDeletePairBtn() {
        return deletePairBtn;
    }

    public JButton getAddCopyColsBtn() {
        return addCopyColsBtn;
    }

    public LinkageColumnButtonPanel() {
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

        addCopyColsBtn = new JButton("_Add columns to be copied");
        addCopyColsBtn.setEnabled(false);
        add(addCopyColsBtn);

        Component horizontalGlue_2 = Box.createHorizontalGlue();
        add(horizontalGlue_2);

        addPairBtn = new JButton("_Add column pair");
        add(addPairBtn);

        Component horizontalGlue_1 = Box.createHorizontalGlue();
        horizontalGlue_1.setMaximumSize(new Dimension(50, 0));
        add(horizontalGlue_1);

        Component horizontalGlue = Box.createHorizontalGlue();
        add(horizontalGlue);

        deletePairBtn = new JButton("_Delete selected pair");
        deletePairBtn.setEnabled(false);
        add(deletePairBtn);

        Component rigidArea_2 = Box.createRigidArea(new Dimension(20, 20));
        rigidArea_2.setPreferredSize(new Dimension(8, 20));
        add(rigidArea_2);

        setBorder(BorderFactory.createRaisedSoftBevelBorder());

    }

    public void updateLocalisedStrings() {
        deletePairBtn.setText(MessageProvider.getMessage("columns.delete"));
        addPairBtn.setText(MessageProvider.getMessage("columns.add"));
        addCopyColsBtn.setText(MessageProvider.getMessage("columns.addcopy"));
    }

}
