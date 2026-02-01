import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.time.format.DateTimeParseException;

public class Storage {
    private static final String FILE_PATH = "./data/YXbot.txt";
    private static final String FOLDER_PATH = "./data/";

    public static ArrayList<Task> loadTasks() throws CorruptedDataException {
        ArrayList<Task> tasks = new ArrayList<>();

        try {
            File folder = new File(FOLDER_PATH);
            if (!folder.exists()) {
                folder.mkdirs();
            }

            File file = new File(FILE_PATH);
            if (!file.exists()) {
                file.createNewFile();
                return tasks; // Empty list for new file
            }

            Scanner sc = new Scanner(file);
            while (sc.hasNextLine()) {
                String line = sc.nextLine().trim();
                if (!line.isEmpty()) {
                    Task task = parseTask(line);
                    if (task != null) {
                        tasks.add(task);
                    }
                }
            }
            sc.close();

        } catch (IOException e){
            System.out.println("Error loading tasks: " + e.getMessage());
        }

        return tasks;
    }

    private static Task parseTask(String line) throws CorruptedDataException{
        try{
            String[] parts = line.split(" \\| ");

            String type = parts[0];
            boolean isDone = parts[1].equals("1");
            String description = parts[2];

            Task task = null;

            switch (type) {
                case "T":
                    task = new Todo(description);
                    break;
                case "D":
                    String by = parts[3];
                    task = new Deadline(description, by);
                    break;
                case "E":
                    String from = parts[3];
                    String to = parts[4];
                    task = new Event(description, from, to);
                    break;
            }

            if (task != null && isDone) {
                task.markAsDone();
            }

            return task;

        }catch (DateTimeParseException e) {
            throw new CorruptedDataException("Invalid date format in task: " + line);
        } catch (Exception e) {
            throw new CorruptedDataException("Error parsing task: " + line);
        }
    }
    public static void saveTasks(ArrayList<Task> tasks) {
        try {
            File folder = new File(FOLDER_PATH);
            if (!folder.exists()) {
                folder.mkdirs();
            }

            FileWriter writer = new FileWriter(FILE_PATH);
            for (Task task : tasks) {
                writer.write(task.toFileFormat() + "\n");
            }
            writer.close();

        } catch (IOException e) {
            System.out.println("Error saving tasks: " + e.getMessage());
        }
    }
}


