package yxbot;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class StorageTest {

    @TempDir
    Path tempDir;

    /**
     * Tests that when loading from a non-existent file, the storage creates the file
     * and returns an empty task list.
     *
     * <p>This test verifies that:
     * <ul>
     *   <li>The file does not exist before the load operation</li>
     *   <li>After calling {@code load()}, the method returns an empty ArrayList</li>
     *   <li>The file is created automatically after the load operation</li>
     * </ul>
     *
     * @throws Exception if an I/O error occurs during file operations
     */
    @Test
    public void load_fileDoesNotExist_createsFileAndReturnsEmptyList() throws Exception {
        Path filePath = tempDir.resolve("data").resolve("tasks.txt");
        Storage storage = new Storage(filePath.toString());

        assertFalse(Files.exists(filePath));

        ArrayList<Task> tasks = storage.load();

        assertNotNull(tasks);
        assertTrue(tasks.isEmpty());

        assertTrue(Files.exists(filePath));
    }

    @Test
    public void save_writesOneLinePerTask_usingToFileFormatOnly() throws Exception {
        Path filePath = tempDir.resolve("tasks.txt");
        Storage storage = new Storage(filePath.toString());

        ArrayList<Task> tasks = new ArrayList<>();
        Task a = new Task("a");
        Task b = new Task("b");
        b.markAsDone();

        tasks.add(a);
        tasks.add(b);

        storage.save(tasks);

        List<String> lines = Files.readAllLines(filePath);
        assertEquals(2, lines.size());
        assertEquals("0", lines.get(0));
        assertEquals("1", lines.get(1));
    }
}
