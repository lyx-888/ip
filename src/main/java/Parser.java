import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Parser {
    private static final DateTimeFormatter DATE_INPUT_FORMAT =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");

    public static Command parse(String input) throws YXBotException {
        if (input.equals("bye")) {
            return new Command(CommandType.BYE);
        } else if (input.equals("list")) {
            return new Command(CommandType.LIST);
        } else if (input.startsWith("mark ")) {
            return parseMark(input);
        } else if (input.startsWith("unmark ")) {
            return parseUnmark(input);
        } else if (input.startsWith("delete ")) {
            return parseDelete(input);
        } else if (input.startsWith("todo ")) {
            return parseTodo(input);
        } else if (input.startsWith("deadline ")) {
            return parseDeadline(input);
        } else if (input.startsWith("event ")) {
            return parseEvent(input);
        } else {
            throw new UnknownCommandException();
        }
    }

    private static Command parseMark(String input) throws InvalidMarkFormatException {
        String[] parts = input.split(" ");
        if (parts.length != 2) {
            throw new InvalidMarkFormatException();
        }
        try {
            int index = Integer.parseInt(parts[1]) - 1;
            return new Command(CommandType.MARK, index);
        } catch (NumberFormatException e) {
            throw new InvalidMarkFormatException();
        }
    }

    private static Command parseUnmark(String input) throws InvalidUnmarkFormatException {
        String[] parts = input.split(" ");
        if (parts.length != 2) {
            throw new InvalidUnmarkFormatException();
        }
        try {
            int index = Integer.parseInt(parts[1]) - 1;
            return new Command(CommandType.UNMARK, index);
        } catch (NumberFormatException e) {
            throw new InvalidUnmarkFormatException();
        }
    }

    private static Command parseDelete(String input) throws InvalidDeleteFormatException {
        String[] parts = input.split(" ");
        if (parts.length != 2) {
            throw new InvalidDeleteFormatException();
        }
        try {
            int index = Integer.parseInt(parts[1]) - 1;
            return new Command(CommandType.DELETE, index);
        } catch (NumberFormatException e) {
            throw new InvalidDeleteFormatException();
        }
    }

    private static Command parseTodo(String input) throws InvalidTodoFormatException {
        String description = input.substring(5).trim();
        if (description.isEmpty()) {
            throw new InvalidTodoFormatException();
        }
        return new Command(CommandType.TODO, new Todo(description));
    }

    private static Command parseDeadline(String input) throws InvalidDeadlineFormatException {
        if (!input.contains(" /by ")) {
            throw new InvalidDeadlineFormatException();
        }

        String content = input.substring(9).trim();
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

        return new Command(CommandType.DEADLINE, new Deadline(description, by));
    }

    private static Command parseEvent(String input) throws InvalidEventFormatException {
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

        return new Command(CommandType.EVENT, new Event(description, from, to));
    }
}
