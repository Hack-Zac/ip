package alfred.task;

/**
 * Represents a todo task.
 */


public class Todo extends Task {
    /**
     * Creates a new todo with the given description.
     *
     * @param description The todo description.
     */

    public Todo(String description) {
        super(description);
    }

    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}
