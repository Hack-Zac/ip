import java.util.Scanner;
public class Alfred {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String[] tasks = new String[100];
        boolean[] completed = new boolean[100];
        int count = 0;

        System.out.println("Hello! I'm Alfred\n");
        System.out.println("What can I do for you?\n");

        while (true) {
            String input = scanner.nextLine();
            if (input.equals("bye")) {
                System.out.println("Bye. Hope to see you again soon!\n");
                break;


            } else if (input.equals("list")) {
                for (int i = 0; i < count; i++) {
                    String status = completed[i] ? "X" : " ";
                    System.out.println((i + 1) + ". [" + status + "] " + tasks[i] +"\n");

                }
            } else if (input.startsWith("mark ")){
                int index = Integer.parseInt(input.substring(5))-1;
                completed[index] = true;
                System.out.println("Nice! I've marked this task as done:\n");

            } else if (input.startsWith("unmark ")){
                int index = Integer.parseInt(input.substring(7)) - 1;
                completed[index] = false;
                System.out.println("OK, I've marked this task as not done yet:");
                System.out.println("  [ ] " + tasks[index]);


            } else {
                tasks[count] = input;
                completed[count] = false;
                count++;
                System.out.println("added: " + input);
            }


            scanner.close();

        }

    }
}
