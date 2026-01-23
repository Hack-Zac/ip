import java.util.Scanner;

public class Alfred {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        Task[] tasks = new Task[100];
        int count = 0;

        System.out.println("Hello! I'm Alfred\n");
        System.out.println("What can I do for you?\n");

        while (true) {
            String input = scanner.nextLine();

            try {
                if (input.equals("bye")) {

                    System.out.println("Bye. Hope to see you again soon!\n");
                    break;

                } else if (input.equals("list")) {

                    for (int i = 0; i < count; i++) {

                        System.out.println((i + 1) + ". " + tasks[i]);

                    }

                } else if (input.startsWith("mark ")) {

                    int index = Integer.parseInt(input.substring(5)) - 1;

                    if (index < 0 || index >= count) {

                        throw new AlfredException("Invalid task number.\n");

                    }

                    tasks[index].markAsDone();
                    System.out.println("Nice! I've marked this task as done:\n");
                    System.out.println("  " + tasks[index] + "\n");

                } else if (input.startsWith("unmark ")) {

                    int index = Integer.parseInt(input.substring(7)) - 1;

                    if (index < 0 || index >= count) {

                        throw new AlfredException("Invalid task number.\n");

                    }

                    tasks[index].markAsNotDone();
                    System.out.println("OK, I've marked this task as not done yet:\n");
                    System.out.println("  " + tasks[index] + "\n");

                } else if (input.startsWith("todo ")) {

                    String description = input.substring(5);

                    if (description.trim().isEmpty()) {

                        throw new AlfredException("The description of a todo cannot be empty.\n");

                    }

                    tasks[count] = new Todo(description);
                    System.out.println("Got it. I've added this task:\n");
                    System.out.println("  " + tasks[count]);
                    count++;
                    System.out.println("Now you have " + count + " tasks in the list.\n");

                } else if (input.startsWith("deadline ")) {

                    String[] parts = input.substring(9).split(" /by ");

                    if (parts.length < 2) {

                        throw new AlfredException("Invalid deadline format.\n");

                    }

                    tasks[count] = new Deadline(parts[0], parts[1]);
                    System.out.println("Got it. I've added this task:\n");
                    System.out.println("  " + tasks[count]);
                    count++;
                    System.out.println("Now you have " + count + " tasks in the list.\n");

                } else if (input.startsWith("event ")) {

                    String[] parts = input.substring(6).split(" /from ");

                    if (parts.length < 2 || !parts[1].contains(" /to ")) {

                        throw new AlfredException("Invalid event format.\n");

                    }

                    String[] timeParts = parts[1].split(" /to ");
                    tasks[count] = new Event(parts[0], timeParts[0], timeParts[1]);
                    System.out.println("Got it. I've added this task:\n");
                    System.out.println("  " + tasks[count]+"\n");
                    count++;
                    System.out.println("Now you have " + count + " tasks in the list.\n");

                } else {

                    throw new AlfredException("I'm sorry, but I don't know what that means :-(\n");

                }

            } catch (AlfredException e) {

                System.out.println("OOPS!!! " + e.getMessage() + "\n");

            } catch (Exception e) {

                System.out.println("OOPS!!! Something went wrong.\n");

            }
        }
    }
}