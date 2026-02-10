package yxbot;

/**
 * Represents a command to find tasks containing a specific keyword in their description.
 * This command searches through all tasks in the task list and displays those that
 * contain the given keyword in a case-insensitive manner.
 */
public class FindCommand extends Command {

    /**
     * Constructs a FindCommand with the specified keyword to search for.
     *
     * @param keyword The keyword to search for in task descriptions
     */
    public FindCommand(String keyword) {
        super(CommandType.FIND, keyword);
    }

    /**
     * Executes the find command by searching through all tasks in the task list
     * for those whose description contains the specified keyword.
     * Displays matching tasks with numbering, or a message if no tasks are found.
     * The search is case-insensitive.
     *
     * @param tasks   The task list containing all tasks to search through
     * @param ui      The user interface for displaying results
     * @param storage The storage handler (not used in this command)
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        assert tasks != null : "TaskList cannot be null";
        assert ui != null : "UI cannot be null";

        String keyword = this.getKeyword();
        assert keyword != null && !keyword.isEmpty() :
                "Search keyword cannot be null or empty";

        ui.showLine();

        int matchCount = 0;
        String keyword = this.getKeyword();

        for (int i = 0; i < tasks.size(); i++) {
            Task task = tasks.get(i);
            assert task != null : "Task in list should not be null";
            assert task.description != null : "Task description should not be null";

            if (task.description.toLowerCase().contains(keyword.toLowerCase())) {
                if (matchCount == 0) {
                    System.out.println("Here are the matching tasks in your list:");
                }
                matchCount++;
                System.out.println(matchCount + "." + task);
            }
        }

        if (matchCount == 0) {
            System.out.println("No tasks found containing: " + keyword);
        }

        ui.showLine();
    }
}