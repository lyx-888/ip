package yxbot;

import java.util.ArrayList;

/**
 * Main chatbot application class.
 * Handles user interactions and task management.
 */
public class YXBot {
    private Storage storage;
    private TaskList tasks;
    private Ui ui;

    /**
     * Constructs a new YXBot instance.
     * Initializes UI, storage, and loads tasks from file.
     * Exits if corrupted data file is detected.
     *
     * @param filePath Path to the data file for storing tasks
     */
    public YXBot(String filePath) {
        ui = new Ui();
        storage = new Storage(filePath);
        try {
            ArrayList<Task> loadedTasks = storage.load();
            tasks = new TaskList(loadedTasks);
        } catch (CorruptedDataException e) {
            ui.showError(e.getMessage());
            System.exit(1);
        }
    }

    public Storage getStorage() {
        return storage;
    }

    public TaskList getTasks() {
        return tasks;
    }

    public Ui getUi() {
        return ui;
    }

    /**
     * Starts the chatbot's main event loop.
     * Processes user commands until "bye" command is received.
     */
    public void run() {
        ui.showWelcome();
        boolean isExit = false;

        while (!isExit) {
            try {
                String input = ui.readCommand();
                Command command = Parser.parse(input);
                isExit = executeCommand(command);
            } catch (YXBotException e) {
                ui.showError(e.getMessage());
            }
        }

        ui.showGoodbye();
        ui.close();
    }

    /**
     * Executes a parsed command.
     *
     * @param command The command to execute
     * @return true if the command is "bye", false otherwise
     * @throws YXBotException if command execution fails
     */
    public boolean executeCommand(Command command) throws YXBotException {
        switch (command.getType()) {
            case BYE:
                return true;

            case LIST:
                ui.showTaskList(tasks);
                return false;

            case MARK:
                handleMarking(command.getIndex(), true);
                return false;

            case UNMARK:
                handleMarking(command.getIndex(), false);
                return false;

            case DELETE:
                handleDelete(command.getIndex());
                return false;

            case TODO:
            case DEADLINE:
            case EVENT:
                handleAddTask(command.getTask());
                return false;

            case FIND:
                command.execute(tasks, ui, storage);
                return false;

            default:
                throw new UnknownCommandException();
        }
    }

    /**
     * Marks/unmarks the task at the given index.
     *
     * @param index 0-based task index
     * @param markDone true to mark as done, false to mark as not done
     * @throws YXBotException if index is invalid
     */
    private void handleMarking(int index, boolean markDone) throws YXBotException {
        validateIndex(index);

        Task task = tasks.get(index);
        if (markDone) {
            task.markAsDone();
        } else {
            task.markAsNotDone();
        }

        ui.showTaskMarked(task, markDone);
        saveTasks();
    }

    /**
     * Deletes the task at the given index.
     *
     * @param index 0-based task index
     * @throws YXBotException if index is invalid
     */
    private void handleDelete(int index) throws YXBotException {
        validateIndex(index);

        Task deletedTask = tasks.delete(index);
        ui.showTaskDeleted(deletedTask, tasks.size());
        saveTasks();
    }

    /**
     * Adds a new task to the list and persists the updated list.
     *
     * @param task task to add
     */
    private void handleAddTask(Task task) throws YXBotException {

        if (tasks.contains(task)) {
            throw new DuplicateTaskException();
        }

        tasks.add(task);
        ui.showTaskAdded(task, tasks.size());
        saveTasks();
    }

    /**
     * Saves current tasks to storage.
     */
    private void saveTasks() {
        storage.save(tasks.getAllTasks());
    }

    /**
     * Validates if a task index is within bounds and task exists.
     *
     * @param index The index to validate
     * @throws InvalidTaskNumberException if index is out of bounds
     * @throws TaskDoesNotExistException if task at index is null
     */
    private void validateIndex(int index) throws YXBotException {
        if (index < 0 || index >= tasks.size()) {
            throw new InvalidTaskNumberException();
        }

        if (tasks.get(index) == null) {
            throw new TaskDoesNotExistException();
        }
    }

    /**
     * Main entry point for the chatbot application.
     *
     * @param args Command line arguments (not used)
     */
    public static void main(String[] args) {
        new YXBot("./data/YXbot.txt").run();
    }
}
