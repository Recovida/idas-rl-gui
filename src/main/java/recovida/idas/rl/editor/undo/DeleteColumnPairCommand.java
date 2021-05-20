package recovida.idas.rl.editor.undo;

import recovida.idas.rl.editor.lang.MessageProvider;
import recovida.idas.rl.editor.pair.ColumnPairManager;

public class DeleteColumnPairCommand extends Command {

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
