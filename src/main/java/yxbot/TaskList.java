package yxbot;

import java.util.ArrayList;

/**
 * Manages a list of tasks.
 * Provides methods to add, retrieve, delete, and query tasks in the task collection.
 */
public class TaskList {
    private ArrayList<Task> tasks;

    /**
     * Constructs an empty TaskList.
     */
    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    /**
     * Constructs a TaskList initialized with an existing list of tasks.
     *
     * @param tasks The ArrayList of tasks to initialize with
     */
    public TaskList(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    /**
     * Adds a task to the list.
     *
     * @param task The task to add
     */
    public void add(Task task) {
        tasks.add(task);
    }

    /**
     * Removes and returns the task at the specified index.
     *
     * @param index The 0-based index of the task to remove
     * @return The removed task
     */
    public Task delete(int index) {
        return tasks.remove(index);
    }

    public Task get(int index) {
        return tasks.get(index);
    }

    /**
     * Returns the number of tasks in the list.
     *
     * @return The size of the task list
     */
    public int size() {
        return tasks.size();
    }

    /**
     * Checks if the task list is empty.
     *
     * @return true if there are no tasks, false otherwise
     */
    public boolean isEmpty() {
        return tasks.isEmpty();
    }

    /**
     * Returns all tasks as an ArrayList.
     *
     * @return The ArrayList containing all tasks
     */
    public ArrayList<Task> getAllTasks() {
        return tasks;
    }

    /**
     * Checks if the task list contains a specific task.
     *
     * @param task The task to check for
     * @return true if the task exists in the list, false otherwise
     */
    public boolean contains(Task task) {
        return tasks.contains(task);
    }
}
