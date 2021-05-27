package recovida.idas.rl.gui.undo;

import recovida.idas.rl.gui.lang.MessageProvider;
import recovida.idas.rl.gui.pair.ColumnPairManager;

public class AddColumnPairCommand extends Command {

    private ColumnPairManager manager;
    int index = 0;
    private String type;
    private String leftColumn;
    private String rightColumn;

    public AddColumnPairCommand(ColumnPairManager manager) {
        this(manager, null, null, null);
    }

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
