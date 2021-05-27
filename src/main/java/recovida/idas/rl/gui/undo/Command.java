package recovida.idas.rl.gui.undo;

public abstract class Command {

    public abstract void undo();

    public abstract void redo();

    public boolean merge(Command that) {
        return false;
    }

    protected boolean merged = false;

    void markAsMerged() {
        merged = true;
    }

    public boolean wasMerged() {
        return merged;
    }

    public abstract String getSummary();

}
