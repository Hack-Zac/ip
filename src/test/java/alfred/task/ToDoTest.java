package alfred.task;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TodoTest {

    @Test
    void constructor_validDescription_createsTodo() {
        Todo todo = new Todo("read book");
        assertEquals("read book", todo.getDescription());
        assertFalse(todo.isDone());
    }

    @Test
    void markAsDone_unmarkedTask_marksAsDone() {
        Todo todo = new Todo("read book");
        todo.markAsDone();
        assertTrue(todo.isDone());
    }

    @Test
    void markAsNotDone_markedTask_marksAsNotDone() {
        Todo todo = new Todo("read book");
        todo.markAsDone();
        todo.markAsNotDone();
        assertFalse(todo.isDone());
    }

    @Test
    void toString_unmarkedTask_correctFormat() {
        Todo todo = new Todo("read book");
        assertEquals("[T][ ] read book", todo.toString());
    }

    @Test
    void toString_markedTask_correctFormat() {
        Todo todo = new Todo("read book");
        todo.markAsDone();
        assertEquals("[T][X] read book", todo.toString());
    }

    @Test
    void setNotes_validNotes_setsNotes() {
        Todo todo = new Todo("read book");
        todo.setNotes("Important!");
        assertEquals("Important!", todo.getNotes());
        assertTrue(todo.hasNotes());
    }
}