package alfred.task;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class EventTest {

    @Test
    public void Format() {
        Event event = new Event("meeting", "Mon 2pm", "4pm");
        assertEquals("[E][ ] meeting (from: Mon 2pm to: 4pm)", event.toString());
    }

    @Test
    public void Value() {
        Event event = new Event("meeting", "Mon 2pm", "4pm");
        assertEquals("Mon 2pm", event.getFrom());
    }

    @Test
    public void correct() {
        Event event = new Event("meeting", "Mon 2pm", "4pm");
        assertEquals("4pm", event.getTo());
    }
}