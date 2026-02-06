package alfred;

import alfred.exception.AlfredException;
import alfred.task.Task;

import java.util.ArrayList;

public class Alfred {
    private static final String DEFAULT_FILE_PATH = "./data/alfred.txt";

    private Storage storage;
    private TaskList tasks;
    private Ui ui;

    /**
     * Creates Alfred with the given file path.
     */
    public Alfred(String filePath) {
        ui = new Ui();
        storage = new Storage(filePath);
        tasks = new TaskList(storage.load());
    }

    /**
     * Creates Alfred with default file path.
     */
    public Alfred() {
        this(DEFAULT_FILE_PATH);
    }

    /**
     * Generates a response for the user's input.
     */
    public String getResponse(String input) {
        try {
            String command = Parser.getCommand(input);

            switch (command) {
                case "bye":
                    return "Bye. Hope to see you again soon!";

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

                default:
                    return "I'm sorry, but I don't know what that means :-(";
            }

        } catch (AlfredException e) {
            return "OOPS!!! " + e.getMessage();
        } catch (Exception e) {
            return "OOPS!!! Something went wrong.";
        }
    }

    private String getTasks() {
        String s = new String();
        for (int i = 0; i < tasks.size(); i++) {
            s = s + ((i + 1))+(". ")+(tasks.get(i))+("\n");
        }
        return s.trim();
    }

    private String parseMark(String input) throws AlfredException {
        int index = Parser.getIndex(input, "mark");
        if (index < 0 || index >= tasks.size()) {
            throw new AlfredException("Invalid task number.");
        }
        tasks.get(index).markAsDone();
        storage.save(tasks);
        return "Nice! I've marked this task as done:\n  " + tasks.get(index);
    }

    private String parseUnmark(String input) throws AlfredException {
        int index = Parser.getIndex(input, "unmark");
        if (index < 0 || index >= tasks.size()) {
            throw new AlfredException("Invalid task number.");
        }
        tasks.get(index).markAsNotDone();
        storage.save(tasks);
        return "OK, I've marked this task as not done yet:\n  " + tasks.get(index);
    }

    private String parseDelete(String input) throws AlfredException {
        int index = Parser.getIndex(input, "delete");
        if (index < 0 || index >= tasks.size()) {
            throw new AlfredException("Invalid task number.");
        }
        Task removed = tasks.delete(index);
        storage.save(tasks);
        return "Noted. I've removed this task:\n  " + removed
                + "\nNow you have " + tasks.size() + " tasks in the list.";
    }

    private String parseTodo(String input) throws AlfredException {
        Task task = Parser.parseTodo(input);
        tasks.add(task);
        storage.save(tasks);
        return "Got it. I've added this task:\n  " + task
                + "\nNow you have " + tasks.size() + " tasks in the list.";
    }

    private String parseDeadline(String input) throws AlfredException {
        Task task = Parser.parseDeadline(input);
        tasks.add(task);
        storage.save(tasks);
        return "Got it. I've added this task:\n  " + task
                + "\nNow you have " + tasks.size() + " tasks in the list.";
    }

    private String parseEvent(String input) throws AlfredException {
        Task task = Parser.parseEvent(input);
        tasks.add(task);
        storage.save(tasks);
        return "Got it. I've added this task:\n  " + task
                + "\nNow you have " + tasks.size() + " tasks in the list.";
    }

    private String parseFind(String input) throws AlfredException {
        String keyword = Parser.getKeyword(input);
        if (keyword.isEmpty()) {
            throw new AlfredException("Please provide a keyword to search.");
        }
        ArrayList<Task> found = tasks.find(keyword);
        String s = "Here are the matching tasks in your list:\n";
        for (int i = 0; i < found.size(); i++) {
            s = s + ((i + 1)) + (". ") + (found.get(i)) + ("\n");
        }
        return s.trim();
    }


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

                    default:
                        throw new AlfredException("I'm sorry, but I don't know what that means :-(");
                }

            } catch (AlfredException e) {
                ui.displayError(e.getMessage());
            } catch (Exception e) {
                ui.displayError("Something went wrong.");
            }
        }
    }

    public static void main(String[] args) {
        new Alfred(DEFAULT_FILE_PATH).run();
    }
}

