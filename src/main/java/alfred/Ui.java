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
        System.out.println("Good Evening Master Wayne!\n");
        System.out.println("How may I be of service today?\n");
    }

    public void displayBye() {
        System.out.println("Goodbye Master Wayne! Happy Hunting!\n");
    }

    public void displayError(String message) {
        System.out.println("CRIKEY!!! " + message + "\n");
    }

    public void displayTaskAdded(Task task, int count) {
        System.out.println("Very Good Sir, I've added this task:\n");
        System.out.println("  " + task);
        System.out.println("Now you have " + count + " tasks in the list.\n");
    }

    public void displayTaskDeleted(Task task, int count) {
        System.out.println("Very Good Sir, I've removed this task:");
        System.out.println("  " + task);
        System.out.println("Now you have " + count + " tasks in the list.");
    }

    public void displayTaskMarked(Task task) {
        System.out.println("Very Good Sir, I've marked this task as done:\n");
        System.out.println("  " + task + "\n");
    }

    public void displayTaskUnmarked(Task task) {
        System.out.println("Very Good Sir, I've marked this task as not done yet:\n");
        System.out.println("  " + task + "\n");
    }

    public void displayTaskList(TaskList tasks) {
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println((i + 1) + ". " + tasks.get(i));
        }
    }

    public void displayFoundTasks(ArrayList<Task> tasks) {
        System.out.println("Here are the matching tasks in your list, Sir:");
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println((i + 1) + ". " + tasks.get(i));
        }
    }
}