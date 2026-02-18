package alfred;

import alfred.task.Task;
import alfred.task.Todo;
import alfred.task.Deadline;
import alfred.task.Event;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

public class Storage {
    private final String filePath;
    private static final DateTimeFormatter DATE_TIME_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public Storage(String filePath) {
        this.filePath = filePath;
    }

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

    private String taskToString(Task task) {
        String type = task instanceof Todo ? "T"
                : task instanceof Deadline ? "D" : "E";
        String done = task.isDone() ? "1" : "0";
        String notes = task.getNotes();

        if (task instanceof Deadline) {
            Deadline d = (Deadline) task;
            String hasTime = d.hasTime() ? "1" : "0";
            String dateStr = d.getBy().format(DATE_TIME_FORMAT);
            return type + " | " + done + " | " + d.getDescription() + " | " + dateStr + " | " + hasTime + " | " + notes;
        } else if (task instanceof Event) {
            Event e = (Event) task;
            String hasTime = e.hasTime() ? "1" : "0";
            String fromStr = e.getFrom().format(DATE_TIME_FORMAT);
            String toStr = e.getTo().format(DATE_TIME_FORMAT);
            return type + " | " + done + " | " + e.getDescription() + " | " + fromStr + " | " + toStr + " | " + hasTime + " | " + notes;
        } else {
            return type + " | " + done + " | " + task.getDescription() + " | " + notes;
        }
    }

    private Task stringToTask(String line) {
        String[] parts = line.split(" \\| ");
        if (parts.length < 3) return null;

        boolean isDone = parts[1].equals("1");
        Task task;
        String notes = "";

        switch (parts[0]) {
            case "T":
                task = new Todo(parts[2]);
                if (parts.length > 3) notes = parts[3];
                break;
            case "D":
                // Format: D | done | description | datetime | hasTime | notes
                boolean deadlineHasTime = parts.length > 4 && parts[4].equals("1");
                if (deadlineHasTime) {
                    LocalDateTime by = LocalDateTime.parse(parts[3], DATE_TIME_FORMAT);
                    task = new Deadline(parts[2], by);
                } else {
                    // Parse as datetime but use date-only constructor
                    LocalDateTime dt = LocalDateTime.parse(parts[3], DATE_TIME_FORMAT);
                    task = new Deadline(parts[2], dt.toLocalDate());
                }
                if (parts.length > 5) notes = parts[5];
                break;
            case "E":
                // Format: E | done | description | from | to | hasTime | notes
                boolean eventHasTime = parts.length > 5 && parts[5].equals("1");
                if (eventHasTime) {
                    LocalDateTime from = LocalDateTime.parse(parts[3], DATE_TIME_FORMAT);
                    LocalDateTime to = LocalDateTime.parse(parts[4], DATE_TIME_FORMAT);
                    task = new Event(parts[2], from, to);
                } else {
                    LocalDateTime fromDt = LocalDateTime.parse(parts[3], DATE_TIME_FORMAT);
                    LocalDateTime toDt = LocalDateTime.parse(parts[4], DATE_TIME_FORMAT);
                    task = new Event(parts[2], fromDt.toLocalDate(), toDt.toLocalDate());
                }
                if (parts.length > 6) notes = parts[6];
                break;
            default:
                return null;
        }

        if (isDone) task.markAsDone();
        if (!notes.isEmpty()) task.setNotes(notes);
        return task;
    }
}
