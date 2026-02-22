package alfred;

import java.util.ArrayList;

import alfred.task.Task;

/**
 * Manages a collection of tasks.
 * Provides methods to add, delete, retrieve, and search tasks.
 */
public class TaskList {
    private final ArrayList<Task> tasks;

    /**
     * Creates an empty TaskList.
     */
    public TaskList() {
        tasks = new ArrayList<>();
    }

    /**
     * Creates a TaskList with the given list of tasks.
     *
     * @param tasks The initial list of tasks.
     */
    public TaskList(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    /**
     * Adds a task to the list.
     *
     * @param task The task to add.
     */
    public void add(Task task) {
        assert task != null : "Task should not be null";
        tasks.add(task);
    }

    /**
     * Deletes a task at the specified index.
     *
     * @param index The zero-based index of the task to delete.
     * @return The deleted task.
     */
    public Task delete(int index) {
        assert index >= 0 : "Index should not be negative";
        assert index < tasks.size() : "Index should be within list bounds";
        return tasks.remove(index);
    }

    /**
     * Retrieves a task at the specified index.
     *
     * @param index The zero-based index of the task to retrieve.
     * @return The task at the specified index.
     */
    public Task get(int index) {
        assert index >= 0 : "Index should not be negative";
        assert index < tasks.size() : "Index should be within list bounds";
        return tasks.get(index);
    }

    /**
     * Returns the number of tasks in the list.
     *
     * @return The number of tasks.
     */
    public int size() {
        return tasks.size();
    }

    /**
     * Returns the underlying list of all tasks.
     *
     * @return The ArrayList containing all tasks.
     */
    public ArrayList<Task> getAllTasks() {
        return tasks;
    }

    /**
     * Searches for tasks containing the specified keyword in their description.
     * The search is case-insensitive.
     *
     * @param keyword The keyword to search for.
     * @return An ArrayList of tasks whose descriptions contain the keyword.
     */
    public ArrayList<Task> find(String keyword) {
        ArrayList<Task> matches = new ArrayList<>();
        String lowerKeyword = keyword.toLowerCase();

        for (Task task : tasks) {
            if (task.getDescription().toLowerCase().contains(lowerKeyword)) {
                matches.add(task);
            }
        }
        return matches;
    }
}