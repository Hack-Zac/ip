package alfred.task;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents an event with a start and end time.
 */
public class Event extends Task {
    private LocalDateTime from;
    private LocalDateTime to;
    private boolean hasTime;

    /**
     * Creates a new event with date and time.
     *
     * @param description The event description.
     * @param from The start date/time.
     * @param to The end date/time.
     */
    public Event(String description, LocalDateTime from, LocalDateTime to) {
        super(description);
        this.from = from;
        this.to = to;
        this.hasTime = true;
    }

    /**
     * Creates a new event with date only (no time).
     *
     * @param description The event description.
     * @param from The start date.
     * @param to The end date.
     */
    public Event(String description, LocalDate from, LocalDate to) {
        super(description);
        this.from = from.atStartOfDay();
        this.to = to.atStartOfDay();
        this.hasTime = false;
    }

    public LocalDateTime getFrom() {
        return from;
    }

    public LocalDateTime getTo() {
        return to;
    }

    public boolean hasTime() {
        return hasTime;
    }

    /**
     * Formats the date/time in proper English (e.g., "27th January 2024, 2:00 PM").
     *
     * @param dateTime The date/time to format.
     * @return The formatted date/time string.
     */
    private String formatDateTimeWithOrdinal(LocalDateTime dateTime) {
        int day = dateTime.getDayOfMonth();
        String suffix = getDayOfMonthSuffix(day);

        if (hasTime) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM yyyy, h:mm a");
            return day + suffix + " " + dateTime.format(formatter);
        } else {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM yyyy");
            return day + suffix + " " + dateTime.format(formatter);
        }
    }

    /**
     * Returns the ordinal suffix for a day of the month.
     *
     * @param day The day of the month (1-31).
     * @return The suffix (st, nd, rd, or th).
     */
    private String getDayOfMonthSuffix(int day) {
        if (day >= 11 && day <= 13) {
            return "th";
        }
        switch (day % 10) {
            case 1:
                return "st";
            case 2:
                return "nd";
            case 3:
                return "rd";
            default:
                return "th";
        }
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + formatDateTimeWithOrdinal(from)
                + " to: " + formatDateTimeWithOrdinal(to) + ")";
    }
}
