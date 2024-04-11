package de.swe.aufgabenmanager._0_plugin.repositories;

import de.swe.aufgabenmanager._3_domain.entities.Task;
import de.swe.aufgabenmanager._3_domain.vo.TaskPriority;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.MockitoAnnotations;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class TaskRepositoryImplTest {
    private TaskRepositoryImpl taskRepository;

    @TempDir
    File tempDir;

    private static final String[] HEADERS = {"ID", "UserId", "GroupId", "Title", "Description", "DueDate", "Completed", "TaskPriority"};

    @BeforeEach
    void setUp() throws IOException {

        MockitoAnnotations.initMocks(this);

        String tempCsvFilePath = tempDir.getAbsolutePath() + "/groups.csv";
        Path pathToCsv = Path.of(tempCsvFilePath);
        Files.createFile(pathToCsv);
        Files.writeString(pathToCsv, String.join(",", HEADERS) + "\n");

        taskRepository = new TaskRepositoryImpl(tempCsvFilePath);
    }

    @Test
    void save() {
        Task task = new Task(999L, 999L, 999L, "Test Title", "Test Description", LocalDateTime.MAX, true, TaskPriority.HIGH);

        taskRepository.save(task);

        Task retrievedTask = taskRepository.findByUserId(999L).get(0);

        assertNotNull(retrievedTask);
        assertEquals(task.getId(), retrievedTask.getId());

    }

    @Test
    void delete() {
        Task task = new Task(999L, 999L, 999L, "Test Title", "Test Description", LocalDateTime.MAX, true, TaskPriority.HIGH);
        taskRepository.save(task);

        taskRepository.delete(task);

        assertEquals(taskRepository.findByUserId(999L).size(), 0);
    }

    @Test
    void findAll() {
        Task task = new Task(999L, 999L, 999L, "Test Title", "Test Description", LocalDateTime.MAX, true, TaskPriority.HIGH);
        taskRepository.save(task);

        assertFalse(taskRepository.findAll().isEmpty());
    }

    @Test
    void findByUserId() {
        Task task = new Task(999L, 999L, 999L, "Test Title", "Test Description", LocalDateTime.MAX, true, TaskPriority.HIGH);
        taskRepository.save(task);

        assertFalse(taskRepository.findByUserId(999L).isEmpty());
    }

    @Test
    void findByGroup() {
        Task task = new Task(999L, 999L, 999L, "Test Title", "Test Description", LocalDateTime.MAX, true, TaskPriority.HIGH);
        taskRepository.save(task);

        assertFalse(taskRepository.findByGroupId(999L).isEmpty());
    }
}