import java.util.Scanner;
public class YXBot {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Task[] list = new Task[100];
        int counter = 0;

        System.out.println("____________________________________________________________");
        System.out.println("Hello! I'm YXBot");
        System.out.println("What can I do for you?");
        System.out.println("____________________________________________________________");
        while (true) {
            String input = sc.nextLine();
            if (input.equals("bye")){
                System.out.println("____________________________________________________________");
                System.out.println("Bye. Hope to see you again soon!");
                System.out.println("____________________________________________________________");
                break;
            }
            else if (input.equals("list")){
                if (counter == 0){
                    System.out.println("Empty list");
                }
                else{
                    System.out.println("____________________________________________________________");
                    System.out.println("Here are the tasks in your list:");
                    for (int i = 0; i < counter; i++){
                        System.out.println((i + 1) + "." + list[i].toString());
                    }
                    System.out.println("____________________________________________________________");
                }
            }
            else if (input.startsWith("mark ")){
                int taskNum = Integer.parseInt(input.split(" ")[1]) - 1;
                if(list[taskNum] == null){
                    System.out.println("____________________________________________________________");
                    System.out.println("task does not exist");
                    System.out.println("____________________________________________________________");
                }
                else{
                    list[taskNum].markAsDone();
                    System.out.println("____________________________________________________________");
                    System.out.println("Nice! I've marked this task as done:");
                    System.out.println("   " + list[taskNum].toString());
                    System.out.println("____________________________________________________________");
                }
            }
            else if (input.startsWith("unmark ")) {
                int taskNum = Integer.parseInt(input.split(" ")[1]) - 1;
                if (list[taskNum] == null) {
                    System.out.println("____________________________________________________________");
                    System.out.println("task does not exist");
                    System.out.println("____________________________________________________________");
                } else {
                    list[taskNum].markAsNotDone();
                    System.out.println("____________________________________________________________");
                    System.out.println("Ok, I've marked this task as not done yet:");
                    System.out.println("  " + list[taskNum].toString());
                    System.out.println("____________________________________________________________");
                }
            }
            else if (input.startsWith(("deadline "))){
                String content = input.substring(9).trim();  // Remove "deadline "
                String[] parts = content.split(" /by ");
                if (parts.length < 2) {
                    System.out.println("Invalid deadline format! Use: deadline [description] /by [time]");
                }
                String description = parts[0].trim();
                String by = parts[1].trim();
                list[counter] = new Deadline(description, by);
                counter++;
                System.out.println("____________________________________________________________");
                System.out.println("Got it. I've added this task:");
                System.out.println("   " + list[counter - 1].toString());
                System.out.println("Now you have " + counter + " tasks in the list.");
                System.out.println("____________________________________________________________");
            }
            else if (input.startsWith(("event "))){
                String content = input.substring(6).trim();  // Remove "event "
                String[] parts = content.split(" /from | /to ");
                if (parts.length < 2) {
                    System.out.println("Invalid event format! Use: event [description] /from [start] /to [end]");
                }
                String description = parts[0].trim();
                String from = parts[1].trim();
                String to = parts[2].trim();
                list[counter] = new Event(description, from, to);
                counter++;
                System.out.println("____________________________________________________________");
                System.out.println("Got it. I've added this task:");
                System.out.println("   " + list[counter - 1].toString());
                System.out.println("Now you have " + counter + " tasks in the list.");
                System.out.println("____________________________________________________________");
            }
            else if (input.startsWith(("todo "))){
                String description = input.substring(5).trim();
                list[counter] = new Todo(description);
                counter++;
                System.out.println("____________________________________________________________");
                System.out.println("Got it. I've added this task:");
                System.out.println("   " + list[counter - 1].toString());
                System.out.println("Now you have " + counter + " tasks in the list.");
                System.out.println("____________________________________________________________");
            }
            else {
                System.out.println("____________________________________________________________");
                System.out.println("added: " + input);
                Task t = new Task(input);
                list[counter] = t;
                counter++;
                System.out.println("____________________________________________________________");
            }
        }
        sc.close();
    }
}
