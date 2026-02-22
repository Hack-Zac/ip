package alfred;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import alfred.exception.AlfredException;
import alfred.task.Deadline;
import alfred.task.Event;
import alfred.task.Task;
import alfred.task.Todo;

/**
 * Handles parsing of user input commands.
 * Provides utility methods to extract command types, task indices,
 * keywords, and to create Task objects from user input strings.
 */
public class Parser {

    private static final int DEADLINE_PREFIX_LENGTH = "deadline ".length();
    private static final String DEADLINE_SEPARATOR = " /by ";
    private static final int NOTE_PREFIX_LENGTH = "note ".length();
    private static final DateTimeFormatter DATE_TIME_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    /**
     * Extracts the command word from user input.
     *
     * @param input The full user input string.
     * @return The first word of the input (the command).
     */
    public static String getCommand(String input) {
        return input.split(" ")[0];
    }

    /**
     * Extracts and parses the task index from user input.
     * Converts from 1-based user input to 0-based index.
     *
     * @param input   The full user input string.
     * @param command The command word (e.g., "mark", "delete").
     * @return The zero-based index of the task.
     * @throws AlfredException If the index is not a valid number.
     */
    public static int getIndex(String input, String command) throws AlfredException {
        String indexStr = input.substring(command.length() + 1).trim();
        try {
            return Integer.parseInt(indexStr) - 1;
        } catch (NumberFormatException e) {
            throw new AlfredException(
                "Please enter a valid task number (e.g., '" + command + " 1'), not '" + indexStr + "', Sir.");
        }
    }

    /**
     * Extracts the search keyword from a find command.
     *
     * @param input The full user input string.
     * @return The search keyword with leading/trailing whitespace trimmed.
     */
    public static String getKeyword(String input) {
        return input.substring(5).trim();
    }

    /**
     * Parses a todo command and creates a Todo task.
     *
     * @param input The full user input string.
     * @return A new Todo task with the specified description.
     * @throws AlfredException If the description is empty.
     */
    public static Task parseTodo(String input) throws AlfredException {
        assert input != null : "I'm Sorry SIr, Input should not be null";
        String description = input.substring(5);
        if (description.trim().isEmpty()) {
            throw new AlfredException("I'm sorry Sir, The description of a todo cannot be empty.");
        }
        return new Todo(description);
    }

    /**
     * Parses a deadline command and creates a Deadline task.
     * Supports both date-only (yyyy-MM-dd) and date-time (yyyy-MM-dd HH:mm) formats.
     *
     * @param input The full user input string.
     * @return A new Deadline task with the specified description and due date.
     * @throws AlfredException If the format is invalid or the date cannot be parsed.
     */
    public static Task parseDeadline(String input) throws AlfredException {
        assert input != null : "I'm Sorry Sir, Input should not be null";
        String[] parts = input.substring(DEADLINE_PREFIX_LENGTH).split(DEADLINE_SEPARATOR);
        if (parts.length < 2) {
            throw new AlfredException("Invalid deadline format. Use: deadline <description> /by <date> [time]");
        }
        String byStr = parts[1].trim();

        try {
            // Check if time is included (contains a space between date and time)
            if (byStr.contains(" ")) {
                // Date + time format: 2024-01-27 14:00
                LocalDateTime by = LocalDateTime.parse(byStr, DATE_TIME_FORMAT);
                return new Deadline(parts[0], by);
            } else {
                // Date only format: 2024-01-27
                LocalDate by = LocalDate.parse(byStr);
                return new Deadline(parts[0], by);
            }
        } catch (Exception e) {
            throw new AlfredException("I'm Sorry Sir, Invalid date format. Use: yyyy-MM-dd or yyyy-MM-dd HH:mm");
        }
    }

    /**
     * Parses an event command and creates an Event task.
     * Supports both date-only (yyyy-MM-dd) and date-time (yyyy-MM-dd HH:mm) formats.
     * Validates that the end date/time is not before the start date/time.
     *
     * @param input The full user input string.
     * @return A new Event task with the specified description and time range.
     * @throws AlfredException If the format is invalid, dates cannot be parsed,
     *                         or end date/time is before start date/time.
     */
    public static Task parseEvent(String input) throws AlfredException {
        assert input != null : "I'm Sorry Sir, Input should not be null";
        String[] parts = input.substring(6).split(" /from ");
        if (parts.length < 2 || !parts[1].contains(" /to ")) {
            throw new AlfredException(
                "I'm Sorry Sir, Invalid event format. Use: event <description> /from <date> [time] /to <date> [time]");
        }
        String[] timeParts = parts[1].split(" /to ");
        String fromStr = timeParts[0].trim();
        String toStr = timeParts[1].trim();

        try {
            // Check if time is included (contains a space between date and time)
            if (fromStr.contains(" ") && toStr.contains(" ")) {
                // Date + time format: 2024-01-27 14:00
                LocalDateTime from = LocalDateTime.parse(fromStr, DATE_TIME_FORMAT);
                LocalDateTime to = LocalDateTime.parse(toStr, DATE_TIME_FORMAT);

                // Add this validation
                if (to.isBefore(from)) {
                    throw new AlfredException("I'm sorry Sir, the end date/time cannot be before the start date/time.");
                }

                return new Event(parts[0], from, to);
            } else {
                // Date only format: 2024-01-27
                LocalDate from = LocalDate.parse(fromStr);
                LocalDate to = LocalDate.parse(toStr);

                // Add this validation
                if (to.isBefore(from)) {
                    throw new AlfredException("I'm sorry Sir, the end date cannot be before the start date.");
                }

                return new Event(parts[0], from, to);
            }
        } catch (AlfredException e) {
            throw e; // Re-throw our custom exception
        } catch (Exception e) {
            throw new AlfredException("I'm Sorry Sir, Invalid date format. Use: yyyy-MM-dd or yyyy-MM-dd HH:mm");
        }
    }

    /**
     * Parses a note command and extracts the task index and note content.
     *
     * @param input The full user input string.
     * @return A String array with [index, note content].
     * @throws AlfredException If the format is invalid or note content is empty.
     */
    public static String[] parseNote(String input) throws AlfredException {
        assert input != null : "Input should not be null, Sir";
        String args = input.substring(NOTE_PREFIX_LENGTH).trim();
        int spaceIndex = args.indexOf(" ");
        if (spaceIndex == -1) {
            throw new AlfredException("I'm Sorry Sir, Invalid note format, Sir. Use: note <task number> <note text>");
        }
        String indexStr = args.substring(0, spaceIndex);
        String noteContent = args.substring(spaceIndex + 1).trim();
        if (noteContent.isEmpty()) {
            throw new AlfredException("I'm Sorry Sir, Note content cannot be empty, Sir.");
        }
        return new String[] {indexStr, noteContent};
    }
}