package alfred.task;

/**
 * Represents an event with a start and end time.
 */

public class Event extends Task {
    public String from;
    public String to;

    /**
     * Creates a new event.
     *
     * @param description The event description.
     * @param from The start time.
     * @param to The end time.
     */


    public Event(String description, String from, String to) {
        super(description);
        this.from = from;
        this.to = to;
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + from + " to: " + to + ")";
    }
}
