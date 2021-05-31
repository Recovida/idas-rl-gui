package recovida.idas.rl.gui.undo;

/**
 * This abstract class represents a command in command design pattern.
 *
 * If {@link #redo()} changes the history state from A to B, then calling
 * {@link #undo()} at B <b>must</b> revert it back to A.
 */
public abstract class AbstractCommand {

    /**
     * Undoes the operation, reverting what was done by {@link #redo()}.
     */
    public abstract void undo();

    /**
     * Performs the operation, either for the first time or after an
     * {@link #undo()}.
     */
    public abstract void redo();

    /**
     * If it is possible to merge this command with the command given as an
     * argument, then this action is converted into the composition of both
     * commands, <code>that</code> is marked as merged, and the function returns
     * <code>true</code>. Otherwise, this command is unchanged and
     * <code>false</code> is returned.
     *
     * @param that the command to be potentially merged with this
     * @return <code>true</code> whether merging was done
     */
    public boolean merge(AbstractCommand that) {
        return false;
    }

    protected boolean merged = false;

    void markAsMerged() {
        merged = true;
    }

    /**
     * Returns <code>true</code> if this command has been merged.
     *
     * @return <code>true</code> if and only if this command has been merged
     *         into another one and should be discarded
     */
    public boolean wasMerged() {
        return merged;
    }

    public abstract String getSummary();

}
