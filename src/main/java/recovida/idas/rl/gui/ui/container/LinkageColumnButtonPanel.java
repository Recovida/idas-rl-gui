package recovida.idas.rl.gui.ui.container;

import java.awt.Component;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

import recovida.idas.rl.gui.lang.MessageProvider;
import javax.swing.ImageIcon;

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
        addCopyColsBtn.setIcon(
                new ImageIcon(LinkageColumnButtonPanel.class.getResource(
                        "/toolbarButtonGraphics/general/AlignJustifyVertical24.gif")));
        addCopyColsBtn.setEnabled(false);
        add(addCopyColsBtn);

        Component hg1 = Box.createHorizontalGlue();
        add(hg1);

        addPairBtn = new JButton("_Add column pair");
        addPairBtn.setIcon(
                new ImageIcon(LinkageColumnButtonPanel.class.getResource(
                        "/toolbarButtonGraphics/table/RowInsertAfter24.gif")));
        add(addPairBtn);

        Component hg2 = Box.createHorizontalGlue();
        hg2.setMaximumSize(new Dimension(50, 0));
        add(hg2);

        Component horizontalGlue = Box.createHorizontalGlue();
        add(horizontalGlue);

        deletePairBtn = new JButton("_Delete selected pair");
        deletePairBtn.setIcon(new ImageIcon(LinkageColumnButtonPanel.class
                .getResource("/toolbarButtonGraphics/table/RowDelete24.gif")));
        deletePairBtn.setEnabled(false);
        add(deletePairBtn);

        Component ra = Box.createRigidArea(new Dimension(20, 20));
        ra.setPreferredSize(new Dimension(8, 20));
        add(ra);

        setBorder(BorderFactory.createRaisedSoftBevelBorder());

    }

    public void updateLocalisedStrings() {
        deletePairBtn.setText(MessageProvider.getMessage("columns.delete"));
        addPairBtn.setText(MessageProvider.getMessage("columns.add"));
        addCopyColsBtn.setText(MessageProvider.getMessage("columns.addcopy"));
    }

}
