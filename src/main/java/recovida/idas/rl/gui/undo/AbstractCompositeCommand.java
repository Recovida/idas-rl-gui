package recovida.idas.rl.gui.undo;

import java.util.List;
import java.util.ListIterator;

/**
 * This abstract class is a skeleton for composite commands, which are
 * successions of commands. Currently, the only unimplemented method is
 * {@link #getSummary()}, which is a method (not a method argument) because it
 * has to be changed according to the current language.
 */
public abstract class AbstractCompositeCommand extends AbstractCommand {

    private List<AbstractCommand> commands;

    /**
     * Creates an instance of a composite command.
     *
     * @param commands the list of commands that comprise this composite command
     */
    public AbstractCompositeCommand(List<AbstractCommand> commands) {
        this.commands = commands;
    }

    @Override
    public void undo() {
        ListIterator<AbstractCommand> it = commands
                .listIterator(commands.size());
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
