package alfred;

import alfred.exception.AlfredException;
import alfred.task.Task;
import alfred.task.Todo;
import alfred.task.Deadline;
import alfred.task.Event;

import java.time.LocalDate;

public class Parser {

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
        String description = input.substring(5);
        if (description.trim().isEmpty()) {
            throw new AlfredException("The description of a todo cannot be empty.");
        }
        return new Todo(description);
    }

    public static Task parseDeadline(String input) throws AlfredException {
        String[] parts = input.substring(9).split(" /by ");
        if (parts.length < 2) {
            throw new AlfredException("Invalid deadline format.");
        }
        LocalDate by = LocalDate.parse(parts[1]);
        return new Deadline(parts[0], by);
    }

    public static Task parseEvent(String input) throws AlfredException {
        String[] parts = input.substring(6).split(" /from ");
        if (parts.length < 2 || !parts[1].contains(" /to ")) {
            throw new AlfredException("Invalid event format.");
        }
        String[] timeParts = parts[1].split(" /to ");
        return new Event(parts[0], timeParts[0], timeParts[1]);
    }
}