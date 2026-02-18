package alfred.task;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents a task with a deadline.
 */
public class Deadline extends Task {
    private LocalDateTime by;
    private boolean hasTime;

    /**
     * Creates a new deadline task with date and time.
     *
     * @param description The task description.
     * @param by The deadline date/time.
     */
    public Deadline(String description, LocalDateTime by) {
        super(description);
        this.by = by;
        this.hasTime = true;
    }

    /**
     * Creates a new deadline task with date only.
     *
     * @param description The task description.
     * @param by The deadline date.
     */
    public Deadline(String description, LocalDate by) {
        super(description);
        this.by = by.atStartOfDay();
        this.hasTime = false;
    }

    public LocalDateTime getBy() {
        return by;
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
        return "[D]" + super.toString() + " (by: " + formatDateTimeWithOrdinal(by) + ")";
    }
}
