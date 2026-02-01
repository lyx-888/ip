import java.util.ArrayList;
import java.util.Scanner;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

/**
 * Main chatbot application class.
 * Handles user interactions and task management.
 */
public class YXBot {
    private static final DateTimeFormatter DATE_INPUT_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("____________________________________________________________");
        System.out.println("Hello! I'm YXBot");
        System.out.println("What can I do for you?");
        System.out.println("____________________________________________________________");
        try {
            ArrayList<Task> list = Storage.loadTasks();
            while (true) {
                try {
                    String input = sc.nextLine();
                    boolean toSave = false;

                    if (input.equals("bye")) {
                        System.out.println("____________________________________________________________");
                        System.out.println("Bye. Hope to see you again soon!");
                        System.out.println("____________________________________________________________");
                        break;

                    } else if (input.equals("list")) {
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
                    } else if (input.equals("deadline")) {
                        throw new InvalidDeadlineFormatException();
                    } else if (input.equals("todo")) {
                        throw new InvalidTodoFormatException();
                    } else if (input.equals("event")) {
                        throw new InvalidEventFormatException();
                    } else if (input.equals("mark")) {
                        throw new InvalidMarkFormatException();
                    } else if (input.equals("unmark")) {
                        throw new InvalidUnmarkFormatException();
                    } else if (input.equals("delete")) {
                        throw new InvalidDeleteFormatException();

                    } else if (input.startsWith("delete ")) {
                        String[] parts = input.split(" ");
                        if (parts.length != 2) {
                            throw new InvalidDeleteFormatException();
                        }
                        int taskNum = Integer.parseInt(parts[1]) - 1;
                        if (taskNum < 0 || taskNum >= list.size()) {
                            throw new InvalidTaskNumberException();
                        }
                        Task removedTask = list.remove(taskNum);

                        System.out.println("____________________________________________________________");
                        System.out.println("Noted. I've removed this task:");
                        System.out.println("   " + removedTask.toString());
                        System.out.println("Now you have " + list.size() + " tasks in the list.");
                        System.out.println("____________________________________________________________");
                        toSave = true;

                    } else if (input.startsWith("mark ")) {
                        String[] parts = input.split(" ");
                        if (parts.length != 2) {
                            throw new InvalidMarkFormatException();
                        }
                        int taskNum = Integer.parseInt(parts[1]) - 1;
                        if (taskNum < 0 || taskNum >= list.size()) {
                            throw new InvalidTaskNumberException();
                        }
                        if (list.get(taskNum) == null) {
                            throw new TaskDoesNotExistException();
                        } else {
                            list.get(taskNum).markAsDone();
                            System.out.println("____________________________________________________________");
                            System.out.println("Nice! I've marked this task as done:");
                            System.out.println("   " + list.get(taskNum).toString());
                            System.out.println("____________________________________________________________");
                            toSave = true;
                        }

                    } else if (input.startsWith("unmark ")) {
                        String[] parts = input.split(" ");
                        if (parts.length != 2) {
                            throw new InvalidUnmarkFormatException();
                        }
                        int taskNum = Integer.parseInt(parts[1]) - 1;
                        if (taskNum < 0 || taskNum >= list.size()) {
                            throw new InvalidTaskNumberException();
                        }
                        if (list.get(taskNum) == null) {
                            throw new TaskDoesNotExistException();
                        } else {
                            list.get(taskNum).markAsNotDone();
                            System.out.println("____________________________________________________________");
                            System.out.println("Ok, I've marked this task as not done yet:");
                            System.out.println("  " + list.get(taskNum).toString());
                            System.out.println("____________________________________________________________");
                            toSave = true;
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
                        if (description.isEmpty() || by.isEmpty()) {
                            throw new InvalidDeadlineFormatException();
                        }

                        try {
                            LocalDateTime.parse(by, DATE_INPUT_FORMAT);
                        } catch (DateTimeParseException e) {
                            throw new InvalidDeadlineFormatException();
                        }

                        list.add(new Deadline(description, by));
                        System.out.println("____________________________________________________________");
                        System.out.println("Got it. I've added this task:");
                        System.out.println("   " + list.get(list.size() - 1).toString());
                        System.out.println("Now you have " + list.size() + " tasks in the list.");
                        System.out.println("____________________________________________________________");
                        toSave = true;

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

                        try {
                            LocalDateTime.parse(from, DATE_INPUT_FORMAT);
                            LocalDateTime.parse(to, DATE_INPUT_FORMAT);
                        } catch (DateTimeParseException e) {
                            throw new InvalidEventFormatException();
                        }

                        list.add(new Event(description, from, to));
                        System.out.println("____________________________________________________________");
                        System.out.println("Got it. I've added this task:");
                        System.out.println("   " + list.get(list.size() - 1).toString());
                        System.out.println("Now you have " + list.size() + " tasks in the list.");
                        System.out.println("____________________________________________________________");
                        toSave = true;

                    } else if (input.startsWith(("todo "))) {
                        String description = input.substring(5).trim();
                        if (description.isEmpty()) {
                            throw new InvalidTodoFormatException();
                        }
                        list.add(new Todo(description));
                        System.out.println("____________________________________________________________");
                        System.out.println("Got it. I've added this task:");
                        System.out.println("   " + list.get(list.size() - 1).toString());
                        System.out.println("Now you have " + list.size() + " tasks in the list.");
                        System.out.println("____________________________________________________________");
                        toSave = true;

                    } else {
                        throw new UnknownCommandException();
                    }

                    if (toSave) {
                        Storage.saveTasks(list);
                    }

                } catch (YXBotException e) {
                    System.out.println(e.getMessage());
                } catch (NumberFormatException e) {
                    System.out.println("Invalid task number!");
                }

            }
        } catch (CorruptedDataException e){
            System.out.println(e.getMessage());
        }
        sc.close();
    }
}
