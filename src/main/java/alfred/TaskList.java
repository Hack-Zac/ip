package alfred;

import alfred.task.Task;
import java.util.ArrayList;

public class TaskList {
    private ArrayList<Task> tasks;

    public TaskList() {
        tasks = new ArrayList<>();
    }

    public TaskList(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    public void add(Task task) {
        assert task != null : "Task should not be null";
        tasks.add(task);
    }

    public Task delete(int index) {
        assert index >= 0 : "Index should not be negative";
        assert index < tasks.size() : "Index should be within list bounds";
        return tasks.remove(index);
    }

    public Task get(int index) {
        assert index >= 0 : "Index should not be negative";
        assert index < tasks.size() : "Index should be within list bounds";
        return tasks.get(index);
    }

    public int size() {
        return tasks.size();
    }

    public ArrayList<Task> getAllTasks() {
        return tasks;
    }

    public ArrayList<Task> find(String keyword) {
        ArrayList<Task> matches = new ArrayList<>();
        for (Task task : tasks) {
            if (task.getDescription().contains(keyword)) {
                matches.add(task);
            }
        }
        return matches;
    }
}