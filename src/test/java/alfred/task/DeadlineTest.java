package alfred.task;

import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.*;

class DeadlineTest {

    @Test
    void constructor_withDateTime_createsDeadline() {
        LocalDateTime dateTime = LocalDateTime.of(2024, 12, 25, 18, 0);
        Deadline deadline = new Deadline("Submit report", dateTime);
        assertEquals("Submit report", deadline.getDescription());
        assertTrue(deadline.hasTime());
    }

    @Test
    void constructor_withDateOnly_createsDeadline() {
        LocalDate date = LocalDate.of(2024, 12, 25);
        Deadline deadline = new Deadline("Submit report", date);
        assertEquals("Submit report", deadline.getDescription());
        assertFalse(deadline.hasTime());
    }

    @Test
    void getBy_returnsCorrectDateTime() {
        LocalDateTime dateTime = LocalDateTime.of(2024, 12, 25, 18, 0);
        Deadline deadline = new Deadline("Submit report", dateTime);
        assertEquals(dateTime, deadline.getBy());
    }

    @Test
    void markAsDone_unmarkedDeadline_marksAsDone() {
        Deadline deadline = new Deadline("Submit report", LocalDate.of(2024, 12, 25));
        deadline.markAsDone();
        assertTrue(deadline.isDone());
    }

    @Test
    void markAsNotDone_markedDeadline_marksAsNotDone() {
        Deadline deadline = new Deadline("Submit report", LocalDate.of(2024, 12, 25));
        deadline.markAsDone();
        deadline.markAsNotDone();
        assertFalse(deadline.isDone());
    }

    @Test
    void toString_withDateTime_correctFormat() {
        LocalDateTime dateTime = LocalDateTime.of(2024, 12, 25, 18, 0);
        Deadline deadline = new Deadline("Submit report", dateTime);
        String result = deadline.toString();
        assertTrue(result.contains("[D]"));
        assertTrue(result.contains("Submit report"));
        assertTrue(result.contains("25th"));
        assertTrue(result.contains("December"));
        assertTrue(result.contains("2024"));
        assertTrue(result.contains("6:00 pm"));
    }

    @Test
    void toString_withDateOnly_correctFormat() {
        LocalDate date = LocalDate.of(2024, 12, 25);
        Deadline deadline = new Deadline("Submit report", date);
        String result = deadline.toString();
        assertTrue(result.contains("[D]"));
        assertTrue(result.contains("Submit report"));
        assertTrue(result.contains("25th"));
        assertTrue(result.contains("December"));
        assertTrue(result.contains("2024"));
        assertFalse(result.contains("pm")); // No time for date-only
    }

    @Test
    void toString_markedDeadline_showsX() {
        Deadline deadline = new Deadline("Submit report", LocalDate.of(2024, 12, 25));
        deadline.markAsDone();
        assertTrue(deadline.toString().contains("[X]"));
    }

    @Test
    void setNotes_validNotes_setsNotes() {
        Deadline deadline = new Deadline("Submit report", LocalDate.of(2024, 12, 25));
        deadline.setNotes("Very important!");
        assertEquals("Very important!", deadline.getNotes());
        assertTrue(deadline.hasNotes());
    }

    @Test
    void hasTime_withDateTime_returnsTrue() {
        LocalDateTime dateTime = LocalDateTime.of(2024, 12, 25, 18, 0);
        Deadline deadline = new Deadline("Submit report", dateTime);
        assertTrue(deadline.hasTime());
    }

    @Test
    void hasTime_withDateOnly_returnsFalse() {
        LocalDate date = LocalDate.of(2024, 12, 25);
        Deadline deadline = new Deadline("Submit report", date);
        assertFalse(deadline.hasTime());
    }

    @Test
    void ordinalSuffix_firstDay_returnsSt() {
        LocalDate date = LocalDate.of(2024, 12, 1);
        Deadline deadline = new Deadline("Test", date);
        assertTrue(deadline.toString().contains("1st"));
    }

    @Test
    void ordinalSuffix_secondDay_returnsNd() {
        LocalDate date = LocalDate.of(2024, 12, 2);
        Deadline deadline = new Deadline("Test", date);
        assertTrue(deadline.toString().contains("2nd"));
    }

    @Test
    void ordinalSuffix_thirdDay_returnsRd() {
        LocalDate date = LocalDate.of(2024, 12, 3);
        Deadline deadline = new Deadline("Test", date);
        assertTrue(deadline.toString().contains("3rd"));
    }

    @Test
    void ordinalSuffix_eleventhDay_returnsTh() {
        LocalDate date = LocalDate.of(2024, 12, 11);
        Deadline deadline = new Deadline("Test", date);
        assertTrue(deadline.toString().contains("11th"));
    }
}