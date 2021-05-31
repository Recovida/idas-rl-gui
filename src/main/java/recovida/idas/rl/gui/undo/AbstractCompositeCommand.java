package recovida.idas.rl.gui.undo;

import java.util.List;
import java.util.ListIterator;

public abstract class AbstractCompositeCommand extends AbstractCommand {

    private List<AbstractCommand> commands;

    public AbstractCompositeCommand(List<AbstractCommand> commands) {
        this.commands = commands;
    }

    @Override
    public void undo() {
        ListIterator<AbstractCommand> it = commands.listIterator(commands.size());
        while (it.hasPrevious())
            it.previous().undo();
    }

    @Override
    public void redo() {
        for (AbstractCommand c : commands)
            c.redo();
    }

    @Override
    public abstract String getSummary();

}
