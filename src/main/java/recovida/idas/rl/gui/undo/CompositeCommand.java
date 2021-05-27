package recovida.idas.rl.gui.undo;

import java.util.List;
import java.util.ListIterator;

public abstract class CompositeCommand extends Command {

    private List<Command> commands;

    public CompositeCommand(List<Command> commands) {
        this.commands = commands;
    }

    @Override
    public void undo() {
        ListIterator<Command> it = commands.listIterator(commands.size());
        while (it.hasPrevious())
            it.previous().undo();
    }

    @Override
    public void redo() {
        for (Command c : commands)
            c.redo();
    }

    @Override
    public abstract String getSummary();

}
