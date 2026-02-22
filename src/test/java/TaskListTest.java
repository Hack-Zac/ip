import alfred.TaskList;
import alfred.task.Task;
import alfred.task.Todo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;

class TaskListTest {
    private TaskList taskList;

    @BeforeEach
    void setUp() {
        taskList = new TaskList();
    }

    @Test
    void add_validTask_increasesSize() {
        assertEquals(0, taskList.size());
        taskList.add(new Todo("test task"));
        assertEquals(1, taskList.size());
    }

    @Test
    void delete_validIndex_removesTask() {
        taskList.add(new Todo("task 1"));
        taskList.add(new Todo("task 2"));
        Task deleted = taskList.delete(0);
        assertEquals("task 1", deleted.getDescription());
        assertEquals(1, taskList.size());
    }

    @Test
    void get_validIndex_returnsTask() {
        taskList.add(new Todo("test task"));
        Task task = taskList.get(0);
        assertEquals("test task", task.getDescription());
    }

    @Test
    void find_matchingKeyword_returnsMatches() {
        taskList.add(new Todo("read book"));
        taskList.add(new Todo("write essay"));
        taskList.add(new Todo("read newspaper"));

        ArrayList<Task> results = taskList.find("read");
        assertEquals(2, results.size());
    }

    @Test
    void find_caseInsensitive_returnsMatches() {
        taskList.add(new Todo("Read Book"));
        taskList.add(new Todo("READ newspaper"));

        ArrayList<Task> results = taskList.find("read");
        assertEquals(2, results.size());
    }

    @Test
    void find_noMatches_returnsEmptyList() {
        taskList.add(new Todo("read book"));
        ArrayList<Task> results = taskList.find("xyz");
        assertTrue(results.isEmpty());
    }
}