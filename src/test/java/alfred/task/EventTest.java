package alfred.task;

import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.*;

public class EventTest {

    @Test
    public void testToString_dateOnly() {
        Event event = new Event("conference",
                LocalDate.of(2024, 1, 27),
                LocalDate.of(2024, 1, 29));
        assertEquals("[E][ ] conference (from: 27th January 2024 to: 29th January 2024)",
                event.toString());
    }

    @Test
    public void testToString_dateTime() {
        Event event = new Event("meeting",
                LocalDateTime.of(2024, 1, 27, 14, 0),
                LocalDateTime.of(2024, 1, 27, 16, 0));
        assertEquals("[E][ ] meeting (from: 27th January 2024, 2:00 pm to: 27th January 2024, 4:00 pm)",
                event.toString());
    }

    @Test
    public void testToString_done() {
        Event event = new Event("conference",
                LocalDate.of(2024, 1, 27),
                LocalDate.of(2024, 1, 29));
        event.markAsDone();
        assertEquals("[E][X] conference (from: 27th January 2024 to: 29th January 2024)",
                event.toString());
    }

    @Test
    public void testHasTime() {
        Event dateOnly = new Event("conference",
                LocalDate.of(2024, 1, 27),
                LocalDate.of(2024, 1, 29));
        Event dateTime = new Event("meeting",
                LocalDateTime.of(2024, 1, 27, 14, 0),
                LocalDateTime.of(2024, 1, 27, 16, 0));
        assertFalse(dateOnly.hasTime());
        assertTrue(dateTime.hasTime());
    }

    @Test
    public void testGetFromTo() {
        LocalDateTime from = LocalDateTime.of(2024, 1, 27, 14, 0);
        LocalDateTime to = LocalDateTime.of(2024, 1, 27, 16, 0);
        Event event = new Event("meeting", from, to);
        assertEquals(from, event.getFrom());
        assertEquals(to, event.getTo());
    }
}
