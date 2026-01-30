package alfred;

import alfred.task.Task;
import java.util.ArrayList;
import java.util.Scanner;

public class Ui {
    private final Scanner scanner;

    public Ui() {
        scanner = new Scanner(System.in);
    }

    public String readCommand() {
        return scanner.nextLine();
    }

    public void displayWelcome() {
        System.out.println("Hello! I'm Alfred\n");
        System.out.println("What can I do for you?\n");
    }

    public void displayBye() {
        System.out.println("Bye. Hope to see you again soon!\n");
    }

    public void displayError(String message) {
        System.out.println("OOPS!!! " + message + "\n");
    }

    public void displayTaskAdded(Task task, int count) {
        System.out.println("Got it. I've added this task:\n");
        System.out.println("  " + task);
        System.out.println("Now you have " + count + " tasks in the list.\n");
    }

    public void displayTaskDeleted(Task task, int count) {
        System.out.println("Noted. I've removed this task:");
        System.out.println("  " + task);
        System.out.println("Now you have " + count + " tasks in the list.");
    }

    public void displayTaskMarked(Task task) {
        System.out.println("Nice! I've marked this task as done:\n");
        System.out.println("  " + task + "\n");
    }

    public void displayTaskUnmarked(Task task) {
        System.out.println("OK, I've marked this task as not done yet:\n");
        System.out.println("  " + task + "\n");
    }

    public void displayTaskList(TaskList tasks) {
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println((i + 1) + ". " + tasks.get(i));
        }
    }

    public void displayFoundTasks(ArrayList<Task> tasks) {
        System.out.println("Here are the matching tasks in your list:");
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println((i + 1) + ". " + tasks.get(i));
        }
    }
}