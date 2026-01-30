package alfred;

import alfred.exception.AlfredException;
import alfred.task.Task;
import java.util.ArrayList;

public class Alfred {
    private static final String FILE_PATH = "./data/alfred.txt";

    private final Storage storage;
    private final TaskList tasks;
    private final Ui ui;

    public Alfred(String filePath) {
        ui = new Ui();
        storage = new Storage(filePath);
        tasks = new TaskList(storage.load());
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

    private void parseMark(String input) throws AlfredException {
        int index = Parser.getIndex(input, "mark");
        if (index < 0 || index >= tasks.size()) {
            throw new AlfredException("Invalid task number.");
        }
        tasks.get(index).markAsDone();
        ui.displayTaskMarked(tasks.get(index));
        storage.save(tasks);
    }

    private void parseUnmark(String input) throws AlfredException {
        int index = Parser.getIndex(input, "unmark");
        if (index < 0 || index >= tasks.size()) {
            throw new AlfredException("Invalid task number.");
        }
        tasks.get(index).markAsNotDone();
        ui.displayTaskUnmarked(tasks.get(index));
        storage.save(tasks);
    }

    private void parseDelete(String input) throws AlfredException {
        int index = Parser.getIndex(input, "delete");
        if (index < 0 || index >= tasks.size()) {
            throw new AlfredException("Invalid task number.");
        }
        Task removed = tasks.delete(index);
        ui.displayTaskDeleted(removed, tasks.size());
        storage.save(tasks);
    }

    private void parseTodo(String input) throws AlfredException {
        Task task = Parser.parseTodo(input);
        tasks.add(task);
        ui.displayTaskAdded(task, tasks.size());
        storage.save(tasks);
    }

    private void parseDeadline(String input) throws AlfredException {
        Task task = Parser.parseDeadline(input);
        tasks.add(task);
        ui.displayTaskAdded(task, tasks.size());
        storage.save(tasks);
    }

    private void parseEvent(String input) throws AlfredException {
        Task task = Parser.parseEvent(input);
        tasks.add(task);
        ui.displayTaskAdded(task, tasks.size());
        storage.save(tasks);
    }

    private void parseFind(String input) throws AlfredException {
        String keyword = Parser.getKeyword(input);
        if (keyword.isEmpty()) {
            throw new AlfredException("Please provide a keyword to search.");
        }
        ArrayList<Task> found = tasks.find(keyword);
        ui.displayFoundTasks(found);
    }

    public static void main(String[] args) {
        new Alfred(FILE_PATH).run();
    }
}