package alfred.task;

/**
 * Represents a task with a description and completion status.
 */
public class Task {
    protected String description;
    protected boolean isDone;
    protected String notes;

    /**
     * Creates a new Task with the given description.
     *
     * @param description The task description.
     */
    public Task(String description) {
        this.description = description;
        this.isDone = false;
        this.notes = "";
    }

    public String getStatusIcon() {
        return (isDone ? "X" : " ");
    }

    public void markAsDone() {
        isDone = true;
    }

    public void markAsNotDone() {
        isDone = false;
    }

    public boolean isDone() {
        return isDone;
    }

    public String getDescription() {
        return description;
    }

    /**
     * Sets notes for this task.
     *
     * @param notes The notes to add.
     */
    public void setNotes(String notes) {
        this.notes = notes;
    }

    /**
     * Gets the notes for this task.
     *
     * @return The notes, or empty string if none.
     */
    public String getNotes() {
        return notes;
    }

    /**
     * Checks if this task has notes.
     *
     * @return True if notes exist.
     */
    public boolean hasNotes() {
        return notes != null && !notes.isEmpty();
    }

    @Override
    public String toString() {
        String base = "[" + getStatusIcon() + "] " + description;
        if (hasNotes()) {
            base += "\n   Notes: " + notes;
        }
        return base;
    }
}