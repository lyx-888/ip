package yxbot;

import java.util.Scanner;

/**
 * Handles all user interface interactions.
 * Manages input/output operations with the user.
 */
public class Ui {
    private Scanner scanner;

    /**
     * Constructs a new Ui instance.
     * Initializes scanner for reading user input.
     */
    public Ui() {
        this.scanner = new Scanner(System.in);
    }

    /**
     * Displays the welcome message.
     */
    public void showWelcome() {
        showLine();
        System.out.println("Hello! I'm YXBot");
        System.out.println("What can I do for you?");
        showLine();
    }

    /**
     * Displays the goodbye message.
     */
    public void showGoodbye() {
        showLine();
        System.out.println("Bye. Hope to see you again soon!");
        showLine();
    }

    /**
     * Displays a horizontal line separator.
     */
    public void showLine() {
        System.out.println("____________________________________________________________");
    }

    /**
     * Displays an error message to the user.
     *
     * @param message The error message to display
     */
    public void showError(String message) {
        System.out.println(message);
    }

    /**
     * Displays confirmation that a task was added.
     *
     * @param task The task that was added
     * @param totalTasks The new total number of tasks
     */
    public void showTaskAdded(Task task, int totalTasks) {
        showLine();
        System.out.println("Got it. I've added this task:");
        System.out.println("   " + task.toString());
        System.out.println("Now you have " + totalTasks + " tasks in the list.");
        showLine();
    }

    public void showTaskDeleted(Task task, int totalTasks) {
        showLine();
        System.out.println("Noted. I've removed this task:");
        System.out.println("   " + task.toString());
        System.out.println("Now you have " + totalTasks + " tasks in the list.");
        showLine();
    }

    public void showTaskMarked(Task task, boolean isDone) {
        showLine();
        if (isDone) {
            System.out.println("Nice! I've marked this task as done:");
        } else {
            System.out.println("Ok, I've marked this task as not done yet:");
        }
        System.out.println("   " + task.toString());
        showLine();
    }

    public void showTaskList(TaskList tasks) {
        if (tasks.isEmpty()) {
            System.out.println("Empty list");
        } else {
            showLine();
            System.out.println("Here are the tasks in your list:");
            for (int i = 0; i < tasks.size(); i++) {
                System.out.println((i + 1) + "." + tasks.get(i).toString());
            }
            showLine();
        }
    }

    public String readCommand() {
        return scanner.nextLine().trim();
    }

    public void close() {
        scanner.close();
    }
}
