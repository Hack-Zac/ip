import java.util.Scanner;
public class Alfred {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        Task[] tasks = new Task[100];
        boolean[] completed = new boolean[100];
        int count = 0;

        System.out.println("Hello! I'm Alfred\n");
        System.out.println("What can I do for you?\n");

        while (true) {
            String input = scanner.nextLine();

            if (input.equals("bye")) {

                System.out.println("Bye. Hope to see you again soon!");
                break;

            } else if (input.equals("list")) {

                for (int i = 0; i < count; i++) {

                    System.out.println((i + 1) + ". " + tasks[i]);

                }
            } else if (input.startsWith("mark ")) {

                int index = Integer.parseInt(input.substring(5)) - 1;
                tasks[index].markAsDone();
                System.out.println("Nice! I've marked this task as done:");
                System.out.println("  " + tasks[index]);

            } else if (input.startsWith("unmark ")) {

                int index = Integer.parseInt(input.substring(7)) - 1;
                tasks[index].markAsNotDone();
                System.out.println("OK, I've marked this task as not done yet:");
                System.out.println("  " + tasks[index]);

            } else if (input.startsWith("todo ")) {

                String desc = input.substring(5);
                tasks[count] = new Todo(desc);
                System.out.println("Got it. I've added this task:");
                System.out.println("  " + tasks[count]);
                count++;
                System.out.println("Now you have " + count + " tasks in the list.");

            } else if (input.startsWith("deadline ")) {
                String[] parts = input.substring(9).split(" /by ");
                tasks[count] = new Deadline(parts[0], parts[1]);
                System.out.println("Got it. I've added this task:");
                System.out.println("  " + tasks[count]);
                count++;
                System.out.println("Now you have " + count + " tasks in the list.");

            } else if (input.startsWith("event ")) {
                String[] parts = input.substring(6).split(" /from ");
                String[] timeParts = parts[1].split(" /to ");
                tasks[count] = new Event(parts[0], timeParts[0], timeParts[1]);
                System.out.println("Got it. I've added this task:");
                System.out.println("  " + tasks[count]);
                count++;
                System.out.println("Now you have " + count + " tasks in the list.");

            }
        }
    }
}

