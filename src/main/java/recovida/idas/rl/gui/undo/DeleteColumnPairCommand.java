package recovida.idas.rl.gui.undo;

import recovida.idas.rl.gui.lang.MessageProvider;
import recovida.idas.rl.gui.pair.ColumnPairManager;

public class DeleteColumnPairCommand extends AbstractCommand {

    private ColumnPairManager manager;

    int index = 0;

    private Object[] contents = new Object[0];

    public DeleteColumnPairCommand(ColumnPairManager manager, int index) {
        this.manager = manager;
        this.index = index;
    }

    @Override
    public void undo() {
        manager.addColumnPair(index, contents);
    }

    @Override
    public void redo() {
        contents = manager.deleteColumnPair(index);
    }

    @Override
    public String getSummary() {
        return MessageProvider.getMessage("columns.delete.summary");
    }

}
