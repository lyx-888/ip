import java.util.Scanner;
import java.util.ArrayList;
public class YXBot {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ArrayList<Task> list = new ArrayList<>();
        int counter = 0;

        System.out.println("____________________________________________________________");
        System.out.println("Hello! I'm YXBot");
        System.out.println("What can I do for you?");
        System.out.println("____________________________________________________________");
        while (true) {
            try {
                String input = sc.nextLine();
                if (input.equals("bye")) {
                    System.out.println("____________________________________________________________");
                    System.out.println("Bye. Hope to see you again soon!");
                    System.out.println("____________________________________________________________");
                    break;
                }
                else if (input.equals("list")) {
                    if (list.isEmpty()) {
                        System.out.println("Empty list");
                    } else {
                        System.out.println("____________________________________________________________");
                        System.out.println("Here are the tasks in your list:");
                        for (int i = 0; i < list.size(); i++) {
                            System.out.println((i + 1) + "." + list.get(i).toString());
                        }
                        System.out.println("____________________________________________________________");
                    }
                }
                else if (input.equals("deadline")){
                    throw new InvalidDeadlineFormatException();
                }
                else if (input.equals("todo")){
                    throw new InvalidTodoFormatException();
                }
                else if (input.equals("event")){
                    throw new InvalidEventFormatException();
                }
                else if (input.equals("mark")){
                    throw new InvalidMarkFormatException();
                }
                else if (input.equals("unmark")){
                    throw new InvalidUnmarkFormatException();
                }
                else if (input.equals("delete")){
                    throw new InvalidDeleteFormatException();
                }
                else if (input.startsWith("delete ")){
                    String[] parts = input.split(" ");
                    if (parts.length != 2) {
                        throw new InvalidDeleteFormatException();
                    }
                    int taskNum = Integer.parseInt(parts[1]) - 1;
                    if (taskNum < 0 || taskNum >= list.size()) {
                        throw new InvalidTaskNumberException();
                    }
                    Task removedTask = list.remove(taskNum);
                    counter--;
                    System.out.println("____________________________________________________________");
                    System.out.println("Noted. I've removed this task:");
                    System.out.println("   " + removedTask.toString());
                    System.out.println("Now you have " + list.size() + " tasks in the list.");
                    System.out.println("____________________________________________________________");

                }
                else if (input.startsWith("mark ")) {
                    String[] parts = input.split(" ");
                    if (parts.length != 2) {
                        throw new InvalidMarkFormatException();
                    }
                    int taskNum = Integer.parseInt(parts[1]) - 1;
                    if (taskNum < 0 || taskNum >= counter) {
                        throw new InvalidTaskNumberException();
                    }
                    if (list.get(taskNum) == null) {
                        throw new TaskDoesNotExistException();
                    }
                    else {
                        list.get(taskNum).markAsDone();
                        System.out.println("____________________________________________________________");
                        System.out.println("Nice! I've marked this task as done:");
                        System.out.println("   " + list.get(taskNum).toString());
                        System.out.println("____________________________________________________________");
                    }
                } else if (input.startsWith("unmark ")) {
                    String[] parts = input.split(" ");
                    if (parts.length != 2) {
                        throw new InvalidUnmarkFormatException();
                    }
                    int taskNum = Integer.parseInt(parts[1]) - 1;
                    if (taskNum < 0 || taskNum >= counter) {
                        throw new InvalidTaskNumberException();
                    }
                    if (list.get(taskNum) == null) {
                        throw new TaskDoesNotExistException();
                    }
                    else {
                        list.get(taskNum).markAsNotDone();
                        System.out.println("____________________________________________________________");
                        System.out.println("Ok, I've marked this task as not done yet:");
                        System.out.println("  " + list.get(taskNum).toString());
                        System.out.println("____________________________________________________________");
                    }
                } else if (input.startsWith(("deadline "))) {
                    if (!input.contains(" /by ")) {
                        throw new InvalidDeadlineFormatException();
                    }
                    String content = input.substring(9).trim();  // Remove "deadline "
                    String[] parts = content.split(" /by ");
                    if (parts.length != 2) {
                        throw new InvalidDeadlineFormatException();
                    }
                    String description = parts[0].trim();
                    String by = parts[1].trim();
                    if (description.isEmpty() || by.isEmpty()){
                        throw new InvalidDeadlineFormatException();
                    }
                    list.add(new Deadline(description, by));
                    counter++;
                    System.out.println("____________________________________________________________");
                    System.out.println("Got it. I've added this task:");
                    System.out.println("   " + list.get(counter - 1).toString());
                    System.out.println("Now you have " + counter + " tasks in the list.");
                    System.out.println("____________________________________________________________");
                } else if (input.startsWith(("event "))) {
                    if (!input.contains(" /from ") || !input.contains(" /to ")) {
                        throw new InvalidEventFormatException();
                    }
                    int fromIndex = input.indexOf(" /from ");
                    int toIndex = input.indexOf(" /to ");
                    if (fromIndex >= toIndex) {
                        throw new InvalidEventFormatException();
                    }
                    String content = input.substring(6).trim();
                    String[] parts = content.split(" /from | /to ");
                    if (parts.length != 3) {
                        throw new InvalidEventFormatException();
                    }
                    String description = parts[0].trim();
                    String from = parts[1].trim();
                    String to = parts[2].trim();
                    if (description.isEmpty() || from.isEmpty() || to.isEmpty()) {
                        throw new InvalidEventFormatException();
                    }
                    list.add(new Event(description, from, to));
                    counter++;
                    System.out.println("____________________________________________________________");
                    System.out.println("Got it. I've added this task:");
                    System.out.println("   " + list.get(counter - 1).toString());
                    System.out.println("Now you have " + counter + " tasks in the list.");
                    System.out.println("____________________________________________________________");
                } else if (input.startsWith(("todo "))) {
                    String description = input.substring(5).trim();
                    if (description.isEmpty()) {
                        throw new InvalidTodoFormatException();
                    }
                    list.add(new Todo(description));
                    counter++;
                    System.out.println("____________________________________________________________");
                    System.out.println("Got it. I've added this task:");
                    System.out.println("   " + list.get(counter - 1).toString());
                    System.out.println("Now you have " + counter + " tasks in the list.");
                    System.out.println("____________________________________________________________");
                }
                else {
                    throw new UnknownCommandException();
                }
            }
            catch (YXBotException e){
                System.out.println(e.getMessage());
            }
            catch (NumberFormatException e){
                System.out.println("Invalid task number!");
            }
        }
        sc.close();
    }
}
