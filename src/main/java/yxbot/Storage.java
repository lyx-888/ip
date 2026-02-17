package yxbot;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.time.format.DateTimeParseException;
/**
 * Handles loading and saving tasks to/from a data file.
 * Manages file operations and data persistence for the chatbot.
 */
public class Storage {
    private String filePath;
    private final ArrayList<String> loadWarnings = new ArrayList<>();

    /**
     * Constructs a Storage instance with the specified file path.
     *
     * @param filePath Path to the data file
     */
    public Storage(String filePath) {
        assert filePath != null && !filePath.isEmpty() :
                "File path cannot be null or empty";
        this.filePath = filePath;
    }


    /**
     * Loads tasks from the data file.
     *
     * @return ArrayList of loaded tasks
     * @throws CorruptedDataException if file cannot be read or contains invalid data
     */
    public ArrayList<Task> load() throws CorruptedDataException {
        ArrayList<Task> tasks = new ArrayList<>();
        loadWarnings.clear();

        try {
            File file = new File(filePath);
            File folder = file.getParentFile();

            if (folder != null && !folder.exists()) {
                folder.mkdirs();
            }

            if (!file.exists()) {
                file.createNewFile();
                return tasks;
            }

            try (Scanner sc = new Scanner(file)) {
                int lineNo = 0;
                while (sc.hasNextLine()) {
                    lineNo++;
                    String line = sc.nextLine().trim();
                    if (line.isEmpty()) {
                        continue;
                    }

                    try {
                        Task task = parseTask(line);
                        tasks.add(task);
                    }  catch (Exception e) {
                        loadWarnings.add("Skipped unreadable line " + lineNo + ": " + line);
                    }
                }
            }

        } catch (IOException e) {
            throw new CorruptedDataException("Error loading tasks: " + e.getMessage());
        }

        return tasks;
    }

    /**
     * Parses a single line from the data file into a Task object.
     *
     * @param line The line to parse
     * @return Parsed Task object
     * @throws CorruptedDataException if line format is invalid
     */
    private Task parseTask(String line) throws CorruptedDataException {
        if (line == null || line.trim().isEmpty()) {
            throw new CorruptedDataException("Empty line");
        }

        String[] parts = line.split(" \\| ");

        if (parts.length < 3) {
            throw new CorruptedDataException("Not enough fields");
        }

        String type = parts[0];
        boolean isDone = "1".equals(parts[1]);
        String description = parts[2];

        if (description == null || description.trim().isEmpty()) {
            throw new CorruptedDataException("Empty description");
        }

        try {
            Task task;

            switch (type) {
                case "T":
                    task = new Todo(description);
                    break;

                case "D":
                    if (parts.length < 4) {
                        throw new CorruptedDataException("Missing deadline date: " + line);
                    }
                    task = new Deadline(description, parts[3]);
                    break;

                case "E":
                    if (parts.length < 5) {
                        throw new CorruptedDataException("Missing event time fields: " + line);
                    }
                    task = new Event(description, parts[3], parts[4]);
                    break;

                default:
                    throw new CorruptedDataException("Unknown task type: " + type);
                }

            if (isDone) {
                task.markAsDone();
            }

            return task;

        } catch (DateTimeParseException e) {
            throw new CorruptedDataException("Invalid date format: " + line);
        } catch (Exception e) {
            throw new CorruptedDataException("Error parsing task: " + line);
        }
    }

    /**
     * Saves tasks to the data file.
     *
     * @param tasks The list of tasks to save
     */
    public void save(ArrayList<Task> tasks) {
        try {
            File file = new File(filePath);
            File folder = file.getParentFile();

            if (folder != null && !folder.exists()) {
                folder.mkdirs();
            }

            try (FileWriter writer = new FileWriter(file)) {
                for (Task task : tasks) {
                    writer.write(task.toFileFormat() + "\n");
                }
            }

        } catch (IOException e) {
            System.out.println("Error saving tasks: " + e.getMessage());
        }
    }
    public ArrayList<String> getLoadWarnings() {
        return new ArrayList<>(loadWarnings);
    }
}