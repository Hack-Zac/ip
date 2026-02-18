package alfred.task;

import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.*;

public class DeadlineTest {

    @Test
    public void testToString_dateOnly() {
        Deadline deadline = new Deadline("submit report", LocalDate.of(2024, 1, 27));
        assertEquals("[D][ ] submit report (by: 27th January 2024)", deadline.toString());
    }

    @Test
    public void testToString_dateTime() {
        Deadline deadline = new Deadline("submit report", LocalDateTime.of(2024, 1, 27, 14, 0));
        assertEquals("[D][ ] submit report (by: 27th January 2024, 2:00 pm)", deadline.toString());
    }

    @Test
    public void testToString_done() {
        Deadline deadline = new Deadline("submit report", LocalDate.of(2024, 1, 27));
        deadline.markAsDone();
        assertEquals("[D][X] submit report (by: 27th January 2024)", deadline.toString());
    }

    @Test
    public void testOrdinalSuffix_1st() {
        Deadline deadline = new Deadline("task", LocalDate.of(2024, 1, 1));
        assertTrue(deadline.toString().contains("1st January"));
    }

    @Test
    public void testOrdinalSuffix_2nd() {
        Deadline deadline = new Deadline("task", LocalDate.of(2024, 1, 2));
        assertTrue(deadline.toString().contains("2nd January"));
    }

    @Test
    public void testOrdinalSuffix_3rd() {
        Deadline deadline = new Deadline("task", LocalDate.of(2024, 1, 3));
        assertTrue(deadline.toString().contains("3rd January"));
    }

    @Test
    public void testOrdinalSuffix_11th() {
        Deadline deadline = new Deadline("task", LocalDate.of(2024, 1, 11));
        assertTrue(deadline.toString().contains("11th January"));
    }

    @Test
    public void testOrdinalSuffix_21st() {
        Deadline deadline = new Deadline("task", LocalDate.of(2024, 1, 21));
        assertTrue(deadline.toString().contains("21st January"));
    }

    @Test
    public void testHasTime() {
        Deadline dateOnly = new Deadline("task", LocalDate.of(2024, 1, 27));
        Deadline dateTime = new Deadline("task", LocalDateTime.of(2024, 1, 27, 14, 0));
        assertFalse(dateOnly.hasTime());
        assertTrue(dateTime.hasTime());
    }

    @Test
    public void testGetBy() {
        LocalDateTime dt = LocalDateTime.of(2024, 1, 27, 14, 0);
        Deadline deadline = new Deadline("task", dt);
        assertEquals(dt, deadline.getBy());
    }
}
