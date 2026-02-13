package alfred;

import alfred.exception.AlfredException;
import alfred.task.Task;
import alfred.task.Todo;
import alfred.task.Deadline;
import alfred.task.Event;
import java.time.LocalDate;

public class Parser {

    private static final int DEADLINE_PREFIX_LENGTH = "deadline ".length();
    private static final String DEADLINE_SEPARATOR = " /by ";
    private static final int NOTE_PREFIX_LENGTH = "note ".length();



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
        assert input != null : "Input should not be null";
        String description = input.substring(5);
        if (description.trim().isEmpty()) {
            throw new AlfredException("The description of a todo cannot be empty.");
        }
        return new Todo(description);
    }

    public static Task parseDeadline(String input) throws AlfredException {
        assert input != null : "Input should not be null";
        String[] parts = input.substring(DEADLINE_PREFIX_LENGTH).split(DEADLINE_SEPARATOR);
        if (parts.length < 2) {
            throw new AlfredException("Invalid deadline format.");
        }
        LocalDate by = LocalDate.parse(parts[1]);
        return new Deadline(parts[0], by);
    }

    public static Task parseEvent(String input) throws AlfredException {
        assert input != null : "Input should not be null";
        String[] parts = input.substring(6).split(" /from ");
        if (parts.length < 2 || !parts[1].contains(" /to ")) {
            throw new AlfredException("Invalid event format.");
        }
        String[] timeParts = parts[1].split(" /to ");
        return new Event(parts[0], timeParts[0], timeParts[1]);
    }


    /**
     * Parses a note command and returns the index and note content.
     *
     * @param input The user input.
     * @return String array with [index, note content].
     * @throws AlfredException If format is invalid.
     */
    public static String[] parseNote(String input) throws AlfredException {
        assert input != null : "Input should not be null";
        String args = input.substring(NOTE_PREFIX_LENGTH).trim();
        int spaceIndex = args.indexOf(" ");
        if (spaceIndex == -1) {
            throw new AlfredException("Invalid note format. Use: note <task number> <note text>");
        }
        String indexStr = args.substring(0, spaceIndex);
        String noteContent = args.substring(spaceIndex + 1).trim();
        if (noteContent.isEmpty()) {
            throw new AlfredException("Note content cannot be empty.");
        }
        return new String[]{indexStr, noteContent};
    }


}