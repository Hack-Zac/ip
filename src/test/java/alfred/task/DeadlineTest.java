package alfred.task;

import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class DeadlineTest {

    @Test
    public void format() {
        Deadline deadline = new Deadline("return book", LocalDate.parse("2024-01-27"));
        assertEquals("[D][ ] return book (by: Jan 27 2024)", deadline.toString());
    }

    @Test
    public void date() {
        LocalDate date = LocalDate.parse("2024-01-27");
        Deadline deadline = new Deadline("return book", date);
        assertEquals(date, deadline.getBy());
    }
}