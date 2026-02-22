package alfred.task;

import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.*;

class EventTest {

    @Test
    void constructor_withDateTime_createsEvent() {
        LocalDateTime from = LocalDateTime.of(2024, 12, 25, 14, 0);
        LocalDateTime to = LocalDateTime.of(2024, 12, 25, 18, 0);
        Event event = new Event("Christmas party", from, to);
        assertEquals("Christmas party", event.getDescription());
        assertTrue(event.hasTime());
    }

    @Test
    void constructor_withDateOnly_createsEvent() {
        LocalDate from = LocalDate.of(2024, 12, 25);
        LocalDate to = LocalDate.of(2024, 12, 26);
        Event event = new Event("Holiday", from, to);
        assertEquals("Holiday", event.getDescription());
        assertFalse(event.hasTime());
    }

    @Test
    void getFrom_returnsCorrectDateTime() {
        LocalDateTime from = LocalDateTime.of(2024, 12, 25, 14, 0);
        LocalDateTime to = LocalDateTime.of(2024, 12, 25, 18, 0);
        Event event = new Event("Party", from, to);
        assertEquals(from, event.getFrom());
    }

    @Test
    void getTo_returnsCorrectDateTime() {
        LocalDateTime from = LocalDateTime.of(2024, 12, 25, 14, 0);
        LocalDateTime to = LocalDateTime.of(2024, 12, 25, 18, 0);
        Event event = new Event("Party", from, to);
        assertEquals(to, event.getTo());
    }

    @Test
    void markAsDone_unmarkedEvent_marksAsDone() {
        Event event = new Event("Party", LocalDate.of(2024, 12, 25), LocalDate.of(2024, 12, 25));
        event.markAsDone();
        assertTrue(event.isDone());
    }

    @Test
    void markAsNotDone_markedEvent_marksAsNotDone() {
        Event event = new Event("Party", LocalDate.of(2024, 12, 25), LocalDate.of(2024, 12, 25));
        event.markAsDone();
        event.markAsNotDone();
        assertFalse(event.isDone());
    }

    @Test
    void toString_withDateTime_correctFormat() {
        LocalDateTime from = LocalDateTime.of(2024, 12, 25, 14, 0);
        LocalDateTime to = LocalDateTime.of(2024, 12, 25, 18, 0);
        Event event = new Event("Christmas party", from, to);
        String result = event.toString();
        assertTrue(result.contains("[E]"));
        assertTrue(result.contains("Christmas party"));
        assertTrue(result.contains("25th"));
        assertTrue(result.contains("December"));
        assertTrue(result.contains("2024"));
        assertTrue(result.contains("from:"));
        assertTrue(result.contains("to:"));
        assertTrue(result.contains("2:00 pm"));
        assertTrue(result.contains("6:00 pm"));
    }

    @Test
    void toString_withDateOnly_correctFormat() {
        LocalDate from = LocalDate.of(2024, 12, 25);
        LocalDate to = LocalDate.of(2024, 12, 26);
        Event event = new Event("Holiday", from, to);
        String result = event.toString();
        assertTrue(result.contains("[E]"));
        assertTrue(result.contains("Holiday"));
        assertTrue(result.contains("25th"));
        assertTrue(result.contains("26th"));
        assertTrue(result.contains("December"));
        assertFalse(result.contains("pm")); // No time for date-only
    }

    @Test
    void toString_markedEvent_showsX() {
        Event event = new Event("Party", LocalDate.of(2024, 12, 25), LocalDate.of(2024, 12, 25));
        event.markAsDone();
        assertTrue(event.toString().contains("[X]"));
    }

    @Test
    void setNotes_validNotes_setsNotes() {
        Event event = new Event("Party", LocalDate.of(2024, 12, 25), LocalDate.of(2024, 12, 25));
        event.setNotes("Bring gifts!");
        assertEquals("Bring gifts!", event.getNotes());
        assertTrue(event.hasNotes());
    }

    @Test
    void hasTime_withDateTime_returnsTrue() {
        LocalDateTime from = LocalDateTime.of(2024, 12, 25, 14, 0);
        LocalDateTime to = LocalDateTime.of(2024, 12, 25, 18, 0);
        Event event = new Event("Party", from, to);
        assertTrue(event.hasTime());
    }

    @Test
    void hasTime_withDateOnly_returnsFalse() {
        LocalDate from = LocalDate.of(2024, 12, 25);
        LocalDate to = LocalDate.of(2024, 12, 26);
        Event event = new Event("Holiday", from, to);
        assertFalse(event.hasTime());
    }

    @Test
    void multiDayEvent_differentDates_works() {
        LocalDate from = LocalDate.of(2024, 12, 25);
        LocalDate to = LocalDate.of(2024, 12, 31);
        Event event = new Event("Holiday break", from, to);
        String result = event.toString();
        assertTrue(result.contains("25th"));
        assertTrue(result.contains("31st"));
    }

    @Test
    void ordinalSuffix_firstDay_returnsSt() {
        LocalDate from = LocalDate.of(2024, 12, 1);
        LocalDate to = LocalDate.of(2024, 12, 1);
        Event event = new Event("Test", from, to);
        assertTrue(event.toString().contains("1st"));
    }

    @Test
    void ordinalSuffix_secondDay_returnsNd() {
        LocalDate from = LocalDate.of(2024, 12, 2);
        LocalDate to = LocalDate.of(2024, 12, 2);
        Event event = new Event("Test", from, to);
        assertTrue(event.toString().contains("2nd"));
    }

    @Test
    void ordinalSuffix_thirdDay_returnsRd() {
        LocalDate from = LocalDate.of(2024, 12, 3);
        LocalDate to = LocalDate.of(2024, 12, 3);
        Event event = new Event("Test", from, to);
        assertTrue(event.toString().contains("3rd"));
    }

    @Test
    void ordinalSuffix_eleventhDay_returnsTh() {
        LocalDate from = LocalDate.of(2024, 12, 11);
        LocalDate to = LocalDate.of(2024, 12, 11);
        Event event = new Event("Test", from, to);
        assertTrue(event.toString().contains("11th"));
    }
}