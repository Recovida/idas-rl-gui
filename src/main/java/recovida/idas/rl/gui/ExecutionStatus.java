package recovida.idas.rl.gui;

/**
 * Represents the execution status.
 */
public enum ExecutionStatus {
    /**
     * Execution is disabled because the configuration file has not been saved.
     */
    UNSAVED(".unsaved"),
    /**
     * Ready to execute.
     */
    READY(""),
    /**
     * Already running.
     */
    RUNNING(".running");

    private String sfx;

    ExecutionStatus(String sfx) {
        this.sfx = sfx;
    }

    /**
     * Returns the suffix appended to the keys used to obtain display messages.
     *
     * @return the suffix for the i18n keys
     */
    public String sfx() {
        return sfx;
    }
}
