        package alfred;

import alfred.exception.AlfredException;
import alfred.task.Task;
import alfred.task.Todo;
import alfred.task.Deadline;
import alfred.task.Event;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Parser {

    private static final int DEADLINE_PREFIX_LENGTH = "deadline ".length();
    private static final String DEADLINE_SEPARATOR = " /by ";
    private static final int NOTE_PREFIX_LENGTH = "note ".length();
    private static final DateTimeFormatter DATE_TIME_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public static String getCommand(String input) {
        return input.split(" ")[0];
    }

    public static int getIndex(String input, String command) {
        return Integer.parseInt(input.substring(command.length() + 1)) - 1;
    }

    public static String getKeyword(String input) {
        return input.substring(5).trim();
    }

    public static Task parseTodo(String input) throws AlfredException {
        assert input != null : "I'm Sorry SIr, Input should not be null";
        String description = input.substring(5);
        if (description.trim().isEmpty()) {
            throw new AlfredException("I'm sorry Sir, The description of a todo cannot be empty.");
        }
        return new Todo(description);
    }

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

    public static Task parseEvent(String input) throws AlfredException {
        assert input != null : "I'm Sorry Sir, Input should not be null";
        String[] parts = input.substring(6).split(" /from ");
        if (parts.length < 2 || !parts[1].contains(" /to ")) {
            throw new AlfredException("I'm Sorry Sir, Invalid event format. Use: event <description> /from <date> [time] /to <date> [time]");
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
                return new Event(parts[0], from, to);
            } else {
                // Date only format: 2024-01-27
                LocalDate from = LocalDate.parse(fromStr);
                LocalDate to = LocalDate.parse(toStr);
                return new Event(parts[0], from, to);
            }
        } catch (Exception e) {
            throw new AlfredException("I'm Sorry Sir, Invalid date format. Use: yyyy-MM-dd or yyyy-MM-dd HH:mm");
        }
    }

    /**
     * Parses a note command and returns the index and note content.
     *
     * @param input The user input.
     * @return String array with [index, note content].
     * @throws AlfredException If format is invalid.
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
        return new String[]{indexStr, noteContent};
    }
}
