package alfred;

import java.time.LocalDate;
import java.util.Scanner;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import alfred.exception.AlfredException;
import task.Task;
import task.Deadline;
import task.Todo;
import task.Event;


public class Alfred {
    private static final String FILE_PATH = "./data/alfred.txt";
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        Task[] tasks = new Task[100];

        int count = 0;
        count = load(tasks);


        System.out.println("Hello! I'm Alfred\n");
        System.out.println("What can I do for you?\n");

        while (true) {
            String input = scanner.nextLine();

            try {
                if (input.equals("bye")) {

                    System.out.println("Bye. Hope to see you again soon!\n");
                    break;

                } else if (input.equals("list")) {

                    for (int i = 0; i < count; i++) {

                        System.out.println((i + 1) + ". " + tasks[i]);

                    }

                } else if (input.startsWith("mark ")) {

                    int index = Integer.parseInt(input.substring(5)) - 1;

                    if (index < 0 || index >= count) {

                        throw new AlfredException("Invalid task number.\n");

                    }

                    tasks[index].markAsDone();
                    System.out.println("Nice! I've marked this task as done:\n");
                    System.out.println("  " + tasks[index] + "\n");
                    save(tasks, count);

                } else if (input.startsWith("unmark ")) {

                    int index = Integer.parseInt(input.substring(7)) - 1;

                    if (index < 0 || index >= count) {

                        throw new AlfredException("Invalid task number.\n");

                    }

                    tasks[index].markAsNotDone();
                    System.out.println("OK, I've marked this task as not done yet:\n");
                    System.out.println("  " + tasks[index] + "\n");
                    save(tasks, count);

                } else if (input.startsWith("delete ")) {

                    int index = Integer.parseInt(input.substring(7)) - 1;

                    if (index < 0 || index >= count) {

                        throw new AlfredException("Invalid task number.");
                    }

                    System.out.println("Noted. I've removed this task:");
                    System.out.println("  " + tasks[index]);

                    for (int i = index; i < count - 1; i++) {

                        tasks[i] = tasks[i + 1];

                    }

                    count--;
                    System.out.println("Now you have " + count + " tasks in the list.");
                    save(tasks, count);

                } else if (input.startsWith("todo ")) {

                    String description = input.substring(5);

                    if (description.trim().isEmpty()) {

                        throw new AlfredException("The description of a todo cannot be empty.\n");

                    }

                    tasks[count] = new Todo(description);
                    System.out.println("Got it. I've added this task:\n");
                    System.out.println("  " + tasks[count]);
                    count++;
                    System.out.println("Now you have " + count + " tasks in the list.\n");
                    save(tasks, count);

                } else if (input.startsWith("deadline ")) {

                    String[] parts = input.substring(9).split(" /by ");

                    if (parts.length < 2) {

                        throw new AlfredException("Invalid deadline format.\n");

                    }

                    LocalDate by = LocalDate.parse(parts[1]);
                    tasks[count] = new Deadline(parts[0], by);

                } else if (input.startsWith("event ")) {

                    String[] parts = input.substring(6).split(" /from ");

                    if (parts.length < 2 || !parts[1].contains(" /to ")) {

                        throw new AlfredException("Invalid event format.\n");

                    }

                    String[] timeParts = parts[1].split(" /to ");
                    tasks[count] = new Event(parts[0], timeParts[0], timeParts[1]);
                    System.out.println("Got it. I've added this task:\n");
                    System.out.println("  " + tasks[count]+"\n");
                    count++;
                    System.out.println("Now you have " + count + " tasks in the list.\n");
                    save(tasks, count);

                } else {

                    throw new AlfredException("I'm sorry, but I don't know what that means :-(\n");

                }

            } catch (AlfredException e) {

                System.out.println("OOPS!!! " + e.getMessage() + "\n");

            } catch (Exception e) {

                System.out.println("OOPS!!! Something went wrong.\n");

            }
        }
    }

    private static void save(Task[] tasks, int count) {
        try {
            File file = new File(FILE_PATH);
            file.getParentFile().mkdirs();

            FileWriter writer = new FileWriter(file);
            for (int i = 0; i < count; i++) {
                writer.write(taskToString(tasks[i]) + "\n");
            }
            writer.close();
        } catch (IOException e) {
            System.out.println("Error saving tasks: " + e.getMessage());
        }
    }

    private static int load(Task[] tasks) {
        File file = new File(FILE_PATH);
        if (!file.exists()) {
            return 0;
        }

        int count = 0;
        try {
            Scanner fileScanner = new Scanner(file);
            while (fileScanner.hasNextLine()) {
                Task task = stringToTask(fileScanner.nextLine());
                if (task != null) {
                    tasks[count++] = task;
                }
            }
            fileScanner.close();
        } catch (IOException e) {
            System.out.println("Error loading tasks: " + e.getMessage());
        }
        return count;
    }

    private static String taskToString(Task task) {
        String type = task instanceof Todo ? "T"
                : task instanceof Deadline ? "D" : "E";
        String done = task.isDone() ? "√" : "X";

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

    private static Task stringToTask(String line) {
        String[] parts = line.split(" \\| ");
        if (parts.length < 3) return null;

        boolean isDone = parts[1].equals("√");
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