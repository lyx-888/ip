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
        System.out.println("_______________________" +
                "_____________________________________");
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
        System.out.println("Now you have " +
                totalTasks + " tasks in the list.");
        showLine();
    }

    /**
     * Displays confirmation that a task was deleted.
     *
     * @param task The task that was removed
     * @param totalTasks The new total number of tasks after deletion
     */
    public void showTaskDeleted(Task task, int totalTasks) {
        showLine();
        System.out.println("Noted. I've removed this task:");
        System.out.println("   " + task.toString());
        System.out.println("Now you have " +
                totalTasks + " tasks in the list.");
        showLine();
    }

    /**
     * Displays confirmation that a task's completion status has been changed.
     *
     * @param task The task whose status was changed
     * @param isDone The new completion status (true for done, false for not done)
     */
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

    /**
     * Displays the complete list of tasks with their indices.
     * If the task list is empty, displays "Empty list" instead.
     *
     * @param tasks The TaskList containing all tasks to display
     */
    public void showTaskList(TaskList tasks) {
        if (tasks.isEmpty()) {
            System.out.println("Empty list");
        } else {
            showLine();
            System.out.println("Here are the tasks in your list:");
            for (int i = 0; i < tasks.size(); i++) {
                System.out.println((i + 1) + "."
                        + tasks.get(i).toString());
            }
            showLine();
        }
    }

    /**
     * Reads and returns the next line of user input from the console.
     * The input is automatically trimmed of leading and trailing whitespace.
     *
     * @return The trimmed user input string
     */
    public String readCommand() {
        return scanner.nextLine().trim();
    }

    /**
     * Closes the scanner and releases any system resources associated with it.
     * Should be called when the UI is no longer needed.
     */
    public void close() {
        scanner.close();
    }
}
