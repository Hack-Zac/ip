package alfred;

import java.util.ArrayList;
import java.util.Scanner;

import alfred.task.Task;

/**
 * Handles all user interface interactions for the CLI version of Alfred.
 * Provides methods for reading user input and displaying various messages.
 */
public class Ui {
    private final Scanner scanner;

    /**
     * Creates a new Ui instance with a Scanner for reading user input.
     */
    public Ui() {
        scanner = new Scanner(System.in);
    }

    /**
     * Reads a line of input from the user.
     *
     * @return The user's input as a string.
     */
    public String readCommand() {
        return scanner.nextLine();
    }

    /**
     * Displays the welcome message when Alfred starts.
     */
    public void displayWelcome() {
        System.out.println("Good Evening Master Wayne!\n");
        System.out.println("How may I be of service today?\n");
        System.out.println("Type 'help' to see available commands.");
    }

    /**
     * Displays the goodbye message when Alfred exits.
     */
    public void displayBye() {
        System.out.println("Goodbye Master Wayne! Happy Hunting!\n");
    }

    /**
     * Displays an error message with the butler-style prefix.
     *
     * @param message The error message to display.
     */
    public void displayError(String message) {
        System.out.println("CRIKEY!!! " + message + "\n");
    }

    /**
     * Displays a confirmation message when a task is added.
     *
     * @param task  The task that was added.
     * @param count The total number of tasks after adding.
     */
    public void displayTaskAdded(Task task, int count) {
        System.out.println("Very Good Sir, I've added this task:\n");
        System.out.println("  " + task);
        System.out.println("Now you have " + count + " tasks in the list.\n");
    }

    /**
     * Displays a confirmation message when a task is deleted.
     *
     * @param task  The task that was deleted.
     * @param count The total number of tasks remaining.
     */
    public void displayTaskDeleted(Task task, int count) {
        System.out.println("Very Good Sir, I've removed this task:");
        System.out.println("  " + task);
        System.out.println("Now you have " + count + " tasks in the list.");
    }

    /**
     * Displays a confirmation message when a task is marked as done.
     *
     * @param task The task that was marked as done.
     */
    public void displayTaskMarked(Task task) {
        System.out.println("Very Good Sir, I've marked this task as done:\n");
        System.out.println("  " + task + "\n");
    }

    /**
     * Displays a confirmation message when a task is marked as not done.
     *
     * @param task The task that was unmarked.
     */
    public void displayTaskUnmarked(Task task) {
        System.out.println("Very Good Sir, I've marked this task as not done yet:\n");
        System.out.println("  " + task + "\n");
    }

    /**
     * Displays all tasks in the task list with numbered indices.
     *
     * @param tasks The TaskList containing tasks to display.
     */
    public void displayTaskList(TaskList tasks) {
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println((i + 1) + ". " + tasks.get(i));
        }
    }

    /**
     * Displays the results of a task search with numbered indices.
     *
     * @param tasks The list of tasks matching the search criteria.
     */
    public void displayFoundTasks(ArrayList<Task> tasks) {
        System.out.println("Here are the matching tasks in your list, Sir:");
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println((i + 1) + ". " + tasks.get(i));
        }
    }
}
