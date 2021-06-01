package recovida.idas.rl.gui.undo;

import recovida.idas.rl.gui.lang.MessageProvider;
import recovida.idas.rl.gui.pair.ColumnPairManager;
import recovida.idas.rl.gui.ui.table.ColumnPairTable;

/**
 * This class represents the command of adding a column pair (represented by a
 * row) in a {@link ColumnPairTable}.
 */
public class AddColumnPairCommand extends AbstractCommand {

    private final ColumnPairManager manager;

    int index = 0;

    private final String type;

    private final String leftColumn;

    private final String rightColumn;

    /**
     * Creates an instance of the command in the special case where the added
     * column is empty.
     *
     * @param manager the column pair manager
     */
    public AddColumnPairCommand(ColumnPairManager manager) {
        this(manager, null, null, null);
    }

    /**
     * Creates an instance of the command.
     *
     * @param manager     the column pair manager
     * @param type        the value of the field "type"
     * @param leftColumn  the value of the field "index_a"
     * @param rightColumn the value of the field "index_b"
     */
    public AddColumnPairCommand(ColumnPairManager manager, String type,
            String leftColumn, String rightColumn) {
        this.manager = manager;
        this.type = type;
        this.leftColumn = leftColumn;
        this.rightColumn = rightColumn;
    }

    @Override
    public void undo() {
        manager.deleteColumnPair(index);
    }

    @Override
    public void redo() {
        index = manager.addColumnPair(type, leftColumn, rightColumn);
    }

    @Override
    public String getSummary() {
        return MessageProvider.getMessage("columns.add.summary");
    }

}
