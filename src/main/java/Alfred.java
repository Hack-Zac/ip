import java.util.Scanner;
public class Alfred {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Hello! I'm Alfred");
        System.out.println("What can I do for you?");
        System.out.println("Bye. Hope to see you again soon!");

        while (true) {
            String input = scanner.nextLine();
            if (input.equals("bye")) {
                System.out.println("Bye. Hope to see you again soon!");
                break;


            } else {
                System.out.println(input);


            }

            scanner.close();

        }

    }
}
