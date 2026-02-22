package alfred;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

import alfred.task.Deadline;
import alfred.task.Event;
import alfred.task.Task;
import alfred.task.Todo;

/**
 * Handles loading and saving of tasks to a file.
 * Tasks are stored in a pipe-delimited format with support for
 * different task types (Todo, Deadline, Event) and their properties.
 */
public class Storage {
    private static final DateTimeFormatter DATE_TIME_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    private static final String PIPE_PLACEHOLDER = "<<PIPE>>";
    private final String filePath;

    /**
     * Creates a Storage instance with the specified file path.
     *
     * @param filePath The path to the file where tasks will be stored.
     */
    public Storage(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Loads tasks from the storage file.
     * Creates an empty list if the file does not exist.
     *
     * @return An ArrayList of Task objects loaded from the file.
     */
    public ArrayList<Task> load() {
        ArrayList<Task> tasks = new ArrayList<>();
        File file = new File(filePath);

        if (!file.exists()) {
            return tasks;
        }

        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                Task task = stringToTask(scanner.nextLine());
                if (task != null) {
                    tasks.add(task);
                }
            }
            scanner.close();
        } catch (IOException e) {
            System.out.println("Error loading tasks: " + e.getMessage());
        }
        return tasks;
    }

    /**
     * Saves all tasks in the TaskList to the storage file.
     * Creates parent directories if they do not exist.
     *
     * @param taskList The TaskList containing tasks to save.
     */
    public void save(TaskList taskList) {
        try {
            assert taskList != null : "TaskList should not be null";
            File file = new File(filePath);
            file.getParentFile().mkdirs();

            FileWriter writer = new FileWriter(file);
            for (int i = 0; i < taskList.size(); i++) {
                writer.write(taskToString(taskList.get(i)) + "\n");
            }
            writer.close();
        } catch (IOException e) {
            System.out.println("Error saving tasks: " + e.getMessage());
        }
    }

    /**
     * Converts a Task object to its string representation for storage.
     * Escapes pipe characters in description and notes to prevent parsing issues.
     * Format varies by task type:
     * <p>
     * Todo: T | done | description | notes
     * Deadline: D | done | description | datetime | hasTime | notes
     * Event: E | done | description | from | to | hasTime | notes
     *
     * @param task The Task to convert to a string.
     * @return The pipe-delimited string representation of the task.
     */
    private String taskToString(Task task) {
        String type = task instanceof Todo ? "T"
            : task instanceof Deadline ? "D" : "E";
        String done = task.isDone() ? "1" : "0";

        // Escape pipe characters
        String description = task.getDescription().replace("|", PIPE_PLACEHOLDER);
        String notes = task.getNotes().replace("|", PIPE_PLACEHOLDER);

        if (task instanceof Deadline d) {
            String hasTime = d.hasTime() ? "1" : "0";
            String dateStr = d.getBy().format(DATE_TIME_FORMAT);
            return type + " | " + done + " | " + description + " | " + dateStr + " | " + hasTime + " | " + notes;
        } else if (task instanceof Event e) {
            String hasTime = e.hasTime() ? "1" : "0";
            String fromStr = e.getFrom().format(DATE_TIME_FORMAT);
            String toStr = e.getTo().format(DATE_TIME_FORMAT);
            return type + " | " + done + " | " + description + " | " + fromStr + " | " + toStr + " | " + hasTime
                + " | " + notes;
        } else {
            return type + " | " + done + " | " + description + " | " + notes;
        }
    }

    /**
     * Parses a string line from the storage file and creates the corresponding Task.
     * Handles unescaping of pipe characters in description and notes.
     * Determines task type from the first field and constructs the appropriate
     * Task subclass with all stored properties.
     *
     * @param line The pipe-delimited string to parse.
     * @return The Task object, or null if the line is invalid.
     */
    private Task stringToTask(String line) {
        // Unescape pipes before splitting
        String[] parts = line.split(" \\| ");
        if (parts.length < 3) {
            return null;
        }

        boolean isDone = parts[1].equals("1");
        Task task;
        String notes = "";

        // Unescape pipe characters in description and notes
        String description = parts[2].replace(PIPE_PLACEHOLDER, "|");

        switch (parts[0]) {
        case "T":
            task = new Todo(description);
            if (parts.length > 3) {
                notes = parts[3].replace(PIPE_PLACEHOLDER, "|");
            }
            break;
        case "D":
            boolean deadlineHasTime = parts.length > 4 && parts[4].equals("1");
            if (deadlineHasTime) {
                LocalDateTime by = LocalDateTime.parse(parts[3], DATE_TIME_FORMAT);
                task = new Deadline(description, by);
            } else {
                LocalDateTime dt = LocalDateTime.parse(parts[3], DATE_TIME_FORMAT);
                task = new Deadline(description, dt.toLocalDate());
            }
            if (parts.length > 5) {
                notes = parts[5].replace(PIPE_PLACEHOLDER, "|");
            }
            break;
        case "E":
            boolean eventHasTime = parts.length > 5 && parts[5].equals("1");
            if (eventHasTime) {
                LocalDateTime from = LocalDateTime.parse(parts[3], DATE_TIME_FORMAT);
                LocalDateTime to = LocalDateTime.parse(parts[4], DATE_TIME_FORMAT);
                task = new Event(description, from, to);
            } else {
                LocalDateTime fromDt = LocalDateTime.parse(parts[3], DATE_TIME_FORMAT);
                LocalDateTime toDt = LocalDateTime.parse(parts[4], DATE_TIME_FORMAT);
                task = new Event(description, fromDt.toLocalDate(), toDt.toLocalDate());
            }
            if (parts.length > 6) {
                notes = parts[6].replace(PIPE_PLACEHOLDER, "|");
            }
            break;
        default:
            return null;
        }

        if (isDone) {
            task.markAsDone();
        }
        if (!notes.isEmpty()) {
            task.setNotes(notes);
        }
        return task;
    }
}
