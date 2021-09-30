package recovida.idas.rl.gui.ui.window;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Toolkit;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import recovida.idas.rl.gui.lang.MessageProvider;
import recovida.idas.rl.gui.ui.table.ColumnPairTable;
import javax.swing.Box;

/**
 * This dialogue is used to ask the user which columns (represented in rows) of
 * type "copy" should be added to a {@link ColumnPairTable}.
 */
public class BulkCopyColumnInclusionDialogue extends JDialog {
    private static final long serialVersionUID = 1609116129275788504L;

    private final JPanel rightPanel;

    private final JPanel leftPanel;

    private final Map<String, JCheckBox> leftMap = new LinkedHashMap<>();

    private final Map<String, JCheckBox> rightMap = new LinkedHashMap<>();

    private final Set<JCheckBox> nonGreyLeft = new HashSet<>();

    private final Set<JCheckBox> nonGreyRight = new HashSet<>();

    @SuppressWarnings("unchecked")
    LinkedList<String>[] result = new LinkedList[] { new LinkedList<>(),
            new LinkedList<>() };

    /**
     * Creates the dialogue.
     *
     * @param parent the parent frame
     */
    public BulkCopyColumnInclusionDialogue(Frame parent) {
        super(parent);
        setFont(parent.getFont());
        setMinimumSize(new Dimension(600, 300));
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setMaximumSize(new Dimension(screenSize.width * 9 / 10,
                screenSize.height * 8 / 10));
        setLocationByPlatform(true);
        setModalityType(ModalityType.APPLICATION_MODAL);
        setModalExclusionType(ModalExclusionType.APPLICATION_EXCLUDE);
        setModal(true);

        JPanel bottomPanel = new JPanel();
        getContentPane().add(bottomPanel, BorderLayout.SOUTH);

        JButton okBtn = new JButton(
                MessageProvider.getMessage("columns.addcopy.insert"));
        okBtn.setIcon(
                new ImageIcon(BulkCopyColumnInclusionDialogue.class.getResource(
                        "/toolbarButtonGraphics/table/RowInsertAfter24.gif")));
        bottomPanel.add(okBtn);
        getContentPane().add(mainPanel, BorderLayout.CENTER);
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.X_AXIS));

        JSplitPane splitPane = new JSplitPane();
        splitPane.setResizeWeight(0.5);
        mainPanel.add(splitPane);

        JScrollPane leftScrollPane = new JScrollPane();
        splitPane.setLeftComponent(leftScrollPane);

        leftPanel = new JPanel();
        leftScrollPane.setViewportView(leftPanel);
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.PAGE_AXIS));

        JPanel firstDatasetLblContainer = new JPanel();
        leftScrollPane.setColumnHeaderView(firstDatasetLblContainer);
        firstDatasetLblContainer.setLayout(
                new BoxLayout(firstDatasetLblContainer, BoxLayout.PAGE_AXIS));

        JLabel firstDatasetLbl = new JLabel(
                MessageProvider.getMessage("columns.addcopy.dataseta"));
        firstDatasetLbl.setAlignmentX(0.5f);
        firstDatasetLbl.setHorizontalAlignment(SwingConstants.CENTER);
        firstDatasetLblContainer.add(firstDatasetLbl);
        firstDatasetLbl
                .setFont(firstDatasetLbl.getFont().deriveFont(Font.BOLD));

        JPanel leftBtnPanel = new JPanel();
        firstDatasetLblContainer.add(leftBtnPanel);
        leftBtnPanel.setLayout(new BoxLayout(leftBtnPanel, BoxLayout.X_AXIS));

        JButton leftSelectUniqueBtn = new JButton(
                MessageProvider.getMessage("columns.addcopy.selectunique"));
        leftBtnPanel.add(leftSelectUniqueBtn);
        setSelectAllEvent(leftSelectUniqueBtn, nonGreyLeft);

        JButton leftSelectAllBtn = new JButton(
                MessageProvider.getMessage("columns.addcopy.selectall"));
        setSelectAllEvent(leftSelectAllBtn, leftMap.values());

        Component firstHorizontalGlue = Box.createHorizontalGlue();
        leftBtnPanel.add(firstHorizontalGlue);
        leftBtnPanel.add(leftSelectAllBtn);

        JScrollPane rightScrollPane = new JScrollPane();
        splitPane.setRightComponent(rightScrollPane);

        JPanel secondDatasetLblContainer = new JPanel();
        rightScrollPane.setColumnHeaderView(secondDatasetLblContainer);
        secondDatasetLblContainer.setLayout(
                new BoxLayout(secondDatasetLblContainer, BoxLayout.PAGE_AXIS));

        JLabel secondDatasetLbl = new JLabel(
                MessageProvider.getMessage("columns.addcopy.datasetb"));
        secondDatasetLbl.setAlignmentX(0.5f);
        secondDatasetLbl.setHorizontalAlignment(SwingConstants.CENTER);
        secondDatasetLbl
                .setFont(secondDatasetLbl.getFont().deriveFont(Font.BOLD));
        secondDatasetLblContainer.add(secondDatasetLbl);

        JPanel rightBtnPanel = new JPanel();
        secondDatasetLblContainer.add(rightBtnPanel);
        rightBtnPanel.setLayout(new BoxLayout(rightBtnPanel, BoxLayout.X_AXIS));

        JButton rightSelectUniqueBtn = new JButton(
                MessageProvider.getMessage("columns.addcopy.selectunique"));
        rightBtnPanel.add(rightSelectUniqueBtn);
        setSelectAllEvent(rightSelectUniqueBtn, nonGreyRight);

        Component secondHorizontalGlue = Box.createHorizontalGlue();
        rightBtnPanel.add(secondHorizontalGlue);

        JButton rightSelectAllBtn = new JButton(
                MessageProvider.getMessage("columns.addcopy.selectall"));
        rightBtnPanel.add(rightSelectAllBtn);
        setSelectAllEvent(rightSelectAllBtn, rightMap.values());

        rightPanel = new JPanel();
        rightScrollPane.setViewportView(rightPanel);
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.PAGE_AXIS));

        JPanel topPanel = new JPanel();
        getContentPane().add(topPanel, BorderLayout.NORTH);
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.PAGE_AXIS));

        JLabel instrLbl1 = new JLabel(
                MessageProvider.getMessage("columns.addcopy.choose"));
        instrLbl1.setAlignmentX(Component.CENTER_ALIGNMENT);
        instrLbl1.setAlignmentY(Component.TOP_ALIGNMENT);
        instrLbl1.setHorizontalAlignment(SwingConstants.CENTER);
        topPanel.add(instrLbl1);

        JLabel instrLbl2 = new JLabel(
                MessageProvider.getMessage("columns.addcopy.warninggrey"));
        instrLbl2.setAlignmentX(Component.CENTER_ALIGNMENT);
        instrLbl2.setAlignmentY(Component.TOP_ALIGNMENT);
        instrLbl2.setHorizontalAlignment(SwingConstants.CENTER);
        topPanel.add(instrLbl2);

        okBtn.addActionListener(e -> {
            for (Entry<String, JCheckBox> entry : leftMap.entrySet())
                if (entry.getValue().isSelected())
                    result[0].add(entry.getKey());
            for (Entry<String, JCheckBox> entry : rightMap.entrySet())
                if (entry.getValue().isSelected())
                    result[1].add(entry.getKey());
            setVisible(false);
            dispose();
        });

    }

    @Override
    public Dimension getPreferredSize() {
        Dimension d = super.getPreferredSize();
        Dimension max = getMaximumSize();
        return new Dimension(Math.min(d.width, max.width),
                Math.min(d.height, max.height));
    }

    private final JPanel mainPanel = new JPanel();

    /**
     * Adds a column to the laft panel.
     *
     * @param item the column name
     * @param grey whether it should be greyed (but still selectable)
     * @return the created checkbox
     */
    public JCheckBox addItemToLeftPanel(String item, boolean grey) {
        JCheckBox cb = addItem(leftPanel, item, grey);
        leftMap.put(item, cb);
        if (!grey)
            nonGreyLeft.add(cb);
        return cb;
    }

    /**
     * Adds a column to the right panel.
     *
     * @param item the column name
     * @param grey whether it should be greyed (but still selectable)
     * @return the created checkbox
     */
    public JCheckBox addItemToRightPanel(String item, boolean grey) {
        JCheckBox cb = addItem(rightPanel, item, grey);
        rightMap.put(item, cb);
        if (!grey)
            nonGreyRight.add(cb);
        return cb;
    }

    protected JCheckBox addItem(JPanel container, String item, boolean grey) {
        JCheckBox cb = new JCheckBox(item);
        if (grey)
            cb.setForeground(Color.GRAY);
        container.add(cb);
        return cb;
    }

    private void setSelectAllEvent(JButton button, Iterable<JCheckBox> items) {
        button.addActionListener(e -> {
            SwingUtilities.invokeLater(() -> {
                for (JCheckBox c : items)
                    c.setSelected(true);
            });
        });
    }

    /**
     * Shows the dialogue and obtains the input from the user.
     *
     * @return a two-element array of lists of columns that the user has
     *         selected (the indices 0 and 1 contain the left and right lists,
     *         respectively)
     */
    public List<String>[] run() {
        pack();
        setVisible(true);
        return result;
    }

}
