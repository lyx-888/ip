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
        assert command != null : "Command should not be null";

        switch (command.getType()) {
        case BYE:
            return true;

        case LIST:
            assert tasks != null : "TaskList should be initialized";
            ui.showTaskList(tasks);
            return false;

        case MARK:
            int markIndex = command.getIndex();
            validateIndex(markIndex);
            Task markTask = tasks.get(markIndex);
            assert markTask != null : "Task at index should not be null";

            markTask.markAsDone();
            ui.showTaskMarked(markTask, true);
            storage.save(tasks.getAllTasks());
            return false;

        case UNMARK:
            int unmarkIndex = command.getIndex();
            validateIndex(unmarkIndex);
            Task unmarkTask = tasks.get(unmarkIndex);
            assert unmarkTask != null : "Task at index should not be null";

            unmarkTask.markAsNotDone();
            ui.showTaskMarked(unmarkTask, false);
            storage.save(tasks.getAllTasks());
            return false;

        case DELETE:
            int deleteIndex = command.getIndex();
            validateIndex(deleteIndex);
            assert deleteIndex < tasks.size() : "Delete index should be within bounds";

            Task deletedTask = tasks.delete(deleteIndex);
            assert deletedTask != null : "Deleted task should not be null";

            ui.showTaskDeleted(deletedTask, tasks.size());
            storage.save(tasks.getAllTasks());
            return false;

        case TODO:
        case DEADLINE:
        case EVENT:
            Task newTask = command.getTask();
            assert newTask != null : "New task should not be null";
            assert newTask.description != null && !newTask.description.isEmpty()
                    : "Task description should not be empty";

            tasks.add(newTask);
            ui.showTaskAdded(newTask, tasks.size());
            storage.save(tasks.getAllTasks());
            return false;

        case FIND:
            command.execute(tasks, ui, storage);
            return false;

        default:
            throw new UnknownCommandException();
        }
    }

    /**
     * Validates if a task index is within bounds and task exists.
     *
     * @param index The index to validate
     * @throws InvalidTaskNumberException if index is out of bounds
     * @throws TaskDoesNotExistException if task at index is null
     */
    private void validateIndex(int index) throws YXBotException {
        assert tasks != null : "TaskList should be initialized when validating index";

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
