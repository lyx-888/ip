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

    private boolean executeCommand(Command command) throws YXBotException {
        switch (command.getType()) {
            case BYE:
                return true;

            case LIST:
                ui.showTaskList(tasks);
                return false;

            case MARK:
                int markIndex = command.getIndex();
                validateIndex(markIndex);
                tasks.get(markIndex).markAsDone();
                ui.showTaskMarked(tasks.get(markIndex), true);
                storage.save(tasks.getAllTasks());
                return false;

            case UNMARK:
                int unmarkIndex = command.getIndex();
                validateIndex(unmarkIndex);
                tasks.get(unmarkIndex).markAsNotDone();
                ui.showTaskMarked(tasks.get(unmarkIndex), false);
                storage.save(tasks.getAllTasks());
                return false;

            case DELETE:
                int deleteIndex = command.getIndex();
                validateIndex(deleteIndex);
                Task deletedTask = tasks.delete(deleteIndex);
                ui.showTaskDeleted(deletedTask, tasks.size());
                storage.save(tasks.getAllTasks());
                return false;

            case TODO:
            case DEADLINE:
            case EVENT:
                Task newTask = command.getTask();
                tasks.add(newTask);
                ui.showTaskAdded(newTask, tasks.size());
                storage.save(tasks.getAllTasks());
                return false;

            default:
                throw new UnknownCommandException();
        }
    }

    private void validateIndex(int index) throws YXBotException {
        if (index < 0 || index >= tasks.size()) {
            throw new InvalidTaskNumberException();
        }

        if (tasks.get(index) == null) {
            throw new TaskDoesNotExistException();
        }
    }

    public static void main(String[] args) {
        new YXBot("./data/YXbot.txt").run();
    }
}
