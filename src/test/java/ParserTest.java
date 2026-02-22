import alfred.Parser;
import alfred.exception.AlfredException;
import alfred.task.Task;
import alfred.task.Todo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ParserTest {

    @Test
    void getCommand_validInput_returnsCommand() {
        Assertions.assertEquals("todo", Parser.getCommand("todo read book"));
        Assertions.assertEquals("deadline", Parser.getCommand("deadline homework /by 2024-12-25"));
        Assertions.assertEquals("list", Parser.getCommand("list"));
    }

    @Test
    void parseTodo_validInput_returnsTodo() throws AlfredException {
        Task task = Parser.parseTodo("todo read book");
        Assertions.assertInstanceOf(Todo.class, task);
        Assertions.assertEquals("read book", task.getDescription());
    }


    @Test
    void getIndex_validInput_returnsIndex() throws AlfredException {
        Assertions.assertEquals(0, Parser.getIndex("mark 1", "mark"));
        Assertions.assertEquals(4, Parser.getIndex("delete 5", "delete"));
    }

}