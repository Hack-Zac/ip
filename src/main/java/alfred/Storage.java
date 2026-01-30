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

        if (task instanceof Deadline) {
            Deadline d = (Deadline) task;
            return type + " | " + done + " | " + d.getDescription() + " | " + d.getBy();
        } else if (task instanceof Event) {
            Event e = (Event) task;
            return type + " | " + done + " | " + e.getDescription() + " | " + e.getFrom() + " | " + e.getTo();
        } else {
            return type + " | " + done + " | " + task.getDescription();
        }
    }

    private Task stringToTask(String line) {
        String[] parts = line.split(" \\| ");
        if (parts.length < 3) return null;

        boolean isDone = parts[1].equals("1");
        Task task;

        switch (parts[0]) {
            case "T":
                task = new Todo(parts[2]);
                break;
            case "D":
                task = new Deadline(parts[2], LocalDate.parse(parts[3]));
                break;
            case "E":
                task = new Event(parts[2], parts[3], parts[4]);
                break;
            default:
                return null;
        }

        if (isDone) task.markAsDone();
        return task;
    }
}
