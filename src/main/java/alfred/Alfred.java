package alfred;

import java.util.ArrayList;

import alfred.exception.AlfredException;
import alfred.task.Task;

/**
 * Main class for the Alfred chatbot application.
 * Alfred is a personal task management assistant styled after Batman's iconic butler,
 * helping users track todos, deadlines, and events.
 */
public class Alfred {
    private static final String DEFAULT_FILE_PATH = "./data/alfred.txt";

    private final Storage storage;
    private final TaskList tasks;
    private final Ui ui;

    /**
     * Creates an Alfred instance with a custom file path for storage.
     *
     * @param filePath The file path where tasks will be saved and loaded from.
     */
    public Alfred(String filePath) {
        ui = new Ui();
        storage = new Storage(filePath);
        tasks = new TaskList(storage.load());
    }

    /**
     * Creates an Alfred instance with the default file path.
     */
    public Alfred() {
        this(DEFAULT_FILE_PATH);
    }

    /**
     * Generates a response for the user's input command.
     * Parses the input and executes the appropriate command.
     *
     * @param input The user's input string.
     * @return The response message to display to the user.
     */
    public String getResponse(String input) {
        try {
            assert input != null : "I'm sorry sir! Input should not be null!";
            String command = Parser.getCommand(input);

            switch (command) {
            case "bye":
                return handleBye();

            case "list":
                return getTasks();

            case "mark":
                return parseMark(input);

            case "unmark":
                return parseUnmark(input);

            case "delete":
                return parseDelete(input);

            case "todo":
                return parseTodo(input);

            case "deadline":
                return parseDeadline(input);

            case "event":
                return parseEvent(input);

            case "find":
                return parseFind(input);

            case "note":
                return parseNote(input);

            case "help":
                return getHelp();

            default:
                return handleUnknown();
            }

        } catch (AlfredException e) {
            return formatError(e.getMessage());
        } catch (Exception e) {
            return formatError("I'm sorry Sir! Something went wrong!");

        }
    }

    /**
     * Formats an error message with the butler-style prefix.
     *
     * @param message The error message to format.
     * @return The formatted error message.
     */
    private String formatError(String message) {
        return "Crikey!!! " + message;
    }

    /**
     * Returns the goodbye message when the user exits.
     *
     * @return The goodbye message.
     */
    private String handleBye() {
        return "Goodbye Sir! Happy Hunting!";
    }

    /**
     * Returns the message for unknown commands.
     *
     * @return The unknown command message.
     */
    private String handleUnknown() {
        return "I'm sorry Sir, but I don't know what that means :-(";
    }

    /**
     * Returns a formatted string of all tasks in the list.
     *
     * @return The formatted task list, or empty message if no tasks exist.
     */
    private String getTasks() {
        String s = "";
        if (tasks.size() == 0) {
            return "Your task list is empty, Sir. Add some tasks to get started!";
        }

        for (int i = 0; i < tasks.size(); i++) {
            s = s + ((i + 1)) + (". ") + (tasks.get(i)) + ("\n");
        }
        return s.trim();
    }

    /**
     * Parses and executes the mark command to mark a task as done.
     *
     * @param input The user input containing the task index.
     * @return The success message with the marked task.
     * @throws AlfredException If the task index is invalid.
     */
    private String parseMark(String input) throws AlfredException {
        int index = Parser.getIndex(input, "mark");
        if (index < 0 || index >= tasks.size()) {
            throw new AlfredException("I'm sorry Sir! That's an invalid task number!\n");
        }
        tasks.get(index).markAsDone();
        storage.save(tasks);
        return "Very good Sir! I've marked this task as done:\n  " + tasks.get(index);
    }

    /**
     * Parses and executes the unmark command to mark a task as not done.
     *
     * @param input The user input containing the task index.
     * @return The success message with the unmarked task.
     * @throws AlfredException If the task index is invalid.
     */
    private String parseUnmark(String input) throws AlfredException {
        int index = Parser.getIndex(input, "unmark");
        if (index < 0 || index >= tasks.size()) {
            throw new AlfredException("I'm sorry Sir! That's an invalid task number!\n");
        }
        tasks.get(index).markAsNotDone();
        storage.save(tasks);
        return "Alright Sir, I've marked this task as not done yet:\n  " + tasks.get(index);
    }

    /**
     * Parses and executes the delete command to remove a task.
     *
     * @param input The user input containing the task index.
     * @return The success message with the deleted task and updated count.
     * @throws AlfredException If the task index is invalid.
     */
    private String parseDelete(String input) throws AlfredException {
        int index = Parser.getIndex(input, "delete");
        if (index < 0 || index >= tasks.size()) {
            throw new AlfredException("I'm sorry Sir! That's an invalid task number!\n");
        }
        Task removed = tasks.delete(index);
        storage.save(tasks);
        return "Very Good Sir. I've removed this task:\n  "
            + removed + "\nNow you have " + tasks.size()
            + " tasks in the list.";
    }

    /**
     * Parses and executes the todo command to add a new todo task.
     *
     * @param input The user input containing the todo description.
     * @return The success message with the added todo.
     * @throws AlfredException If the description is empty.
     */
    private String parseTodo(String input) throws AlfredException {
        Task task = Parser.parseTodo(input);
        tasks.add(task);
        storage.save(tasks);
        return "Very Good Sir, I've added this task:\n  "
            + task + "\nNow you have " + tasks.size() + " tasks in the list.";
    }

    /**
     * Parses and executes the deadline command to add a new deadline task.
     *
     * @param input The user input containing the deadline details.
     * @return The success message with the added deadline.
     * @throws AlfredException If the format is invalid or date cannot be parsed.
     */
    private String parseDeadline(String input) throws AlfredException {
        Task task = Parser.parseDeadline(input);
        tasks.add(task);
        storage.save(tasks);
        return "Very Good Sir, I've added this task:\n  " + task + "\nNow you have " + tasks.size()
            + " tasks in the list.";
    }

    /**
     * Parses and executes the event command to add a new event task.
     *
     * @param input The user input containing the event details.
     * @return The success message with the added event.
     * @throws AlfredException If the format is invalid or dates cannot be parsed.
     */
    private String parseEvent(String input) throws AlfredException {
        Task task = Parser.parseEvent(input);
        tasks.add(task);
        storage.save(tasks);
        return "Very Good Sir, I've added this task:\n  " + task + "\nNow you have " + tasks.size()
            + " tasks in the list.";
    }

    /**
     * Parses and executes the find command to search for tasks by keyword.
     *
     * @param input The user input containing the search keyword.
     * @return The list of matching tasks or no results message.
     * @throws AlfredException If the keyword is empty.
     */
    private String parseFind(String input) throws AlfredException {
        String keyword = Parser.getKeyword(input);
        if (keyword.isEmpty()) {
            throw new AlfredException("Please provide a keyword to search, Sir.");
        }
        ArrayList<Task> found = tasks.find(keyword);

        if (found.isEmpty()) {
            return "No tasks found matching \"" + keyword + "\", Sir.";
        }

        String s = "Here are the matching tasks in your list, Sir:\n";
        for (int i = 0; i < found.size(); i++) {
            s = s + ((i + 1)) + (". ") + (found.get(i)) + ("\n");
        }
        return s.trim();
    }

    /**
     * Parses and executes the note command to add a note to a task.
     *
     * @param input The user input containing task index and note content.
     * @return The success message with the updated task.
     * @throws AlfredException If the format or index is invalid.
     */
    private String parseNote(String input) throws AlfredException {
        String[] noteData = Parser.parseNote(input);
        int index = Integer.parseInt(noteData[0]) - 1;
        String noteContent = noteData[1];

        if (!isValidIndex(index)) {
            throw new AlfredException("I'm sorry Sir! That's an invalid task number!\n");
        }

        tasks.get(index).setNotes(noteContent);
        storage.save(tasks);
        return "Very Good Sir, I've added a note to this task:\n  " + tasks.get(index);
    }

    /**
     * Returns the help message listing all available commands.
     *
     * @return The formatted help message with command descriptions.
     */
    private String getHelp() {
        return "Here are the available commands, Sir:\n\n" + "📋 TASK MANAGEMENT:\n"
            + "  todo <description> - Add a todo task\n"
            + "  deadline <description> /by <date> [time] - Add a deadline\n"
            + "  event <description> /from <date> [time] /to <date> [time] - Add an event\n"
            + "  list - Show all tasks\n" + "  find <keyword> - Search tasks\n"
            + "  mark <number> - Mark task as done\n" + "  unmark <number> - Mark task as not done\n"
            + "  delete <number> - Delete a task\n" + "  note <number> <text> - Add a note to a task\n\n"
            + "📅 DATE FORMATS:\n" + "  Date only: yyyy-MM-dd (e.g., 2024-12-25)\n"
            + "  Date + time: yyyy-MM-dd HH:mm (e.g., 2024-12-25 18:00)\n\n" + "👋 bye - Exit the application";
    }

    /**
     * Runs the command-line interface version of Alfred.
     * Continuously reads user input and processes commands until "bye" is entered.
     */
    public void run() {
        ui.displayWelcome();

        while (true) {
            String input = ui.readCommand();

            try {
                String command = Parser.getCommand(input);

                switch (command) {
                case "bye":
                    ui.displayBye();
                    return;

                case "list":
                    ui.displayTaskList(tasks);
                    break;

                case "mark":
                    parseMark(input);
                    break;

                case "unmark":
                    parseUnmark(input);
                    break;

                case "delete":
                    parseDelete(input);
                    break;

                case "todo":
                    parseTodo(input);
                    break;

                case "deadline":
                    parseDeadline(input);
                    break;

                case "event":
                    parseEvent(input);
                    break;

                case "find":
                    parseFind(input);
                    break;


                case "note":
                    parseNote(input);
                    break;


                default:
                    throw new AlfredException("I'm sorry Sir, but I don't know what that means :-(");
                }

            } catch (AlfredException e) {
                ui.displayError(e.getMessage());
            } catch (Exception e) {
                ui.displayError("Crikey!!! Something went wrong.");
            }
        }
    }

    /**
     * Checks if the given index is valid for the task list.
     *
     * @param index The zero-based index to check.
     * @return True if the index is valid, false otherwise.
     */
    private boolean isValidIndex(int index) {
        return index >= 0 && index < tasks.size();
    }

    /**
     * Main entry point for the CLI application.
     *
     * @param args Command line arguments (not used).
     */
    public static void main(String[] args) {
        new Alfred(DEFAULT_FILE_PATH).run();
    }
}
