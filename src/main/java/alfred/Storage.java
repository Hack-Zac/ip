package alfred;

import alfred.task.Task;
import alfred.task.Todo;
import alfred.task.Deadline;
import alfred.task.Event;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

public class Storage {
    private final String filePath;

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
            return type + " | " + done + " | " + d.getDescription() + " | " + d.getBy() + " | " + notes;
        } else if (task instanceof Event) {
            Event e = (Event) task;
            return type + " | " + done + " | " + e.getDescription() + " | " + e.getFrom() + " | " + e.getTo() + " | " + notes;
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
                task = new Deadline(parts[2], LocalDate.parse(parts[3]));
                if (parts.length > 4) notes = parts[4];
                break;
            case "E":
                task = new Event(parts[2], parts[3], parts[4]);
                if (parts.length > 5) notes = parts[5];
                break;
            default:
                return null;
        }

        if (isDone) task.markAsDone();
        if (!notes.isEmpty()) task.setNotes(notes);
        return task;
    }
}
