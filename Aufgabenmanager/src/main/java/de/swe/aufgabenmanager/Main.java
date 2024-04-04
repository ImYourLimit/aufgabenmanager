package de.swe.aufgabenmanager;

import de.swe.aufgabenmanager._0_plugin.cli.CliStart;
import de.swe.aufgabenmanager._0_plugin.repositories.TaskRepositoryImpl;
import de.swe.aufgabenmanager._3_domain.entities.Task;
import de.swe.aufgabenmanager._3_domain.entities.ITaskRepository;
import de.swe.aufgabenmanager._3_domain.vo.TaskPriority;

import java.time.LocalDateTime;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        Task task = new Task(1L, 1L, "Test Task", "Test Description", LocalDateTime.now(), false, TaskPriority.HIGH);
        ITaskRepository ITaskRepository = new TaskRepositoryImpl();
        ITaskRepository.save(task);

        CliStart cliStart = new CliStart();
        cliStart.start();
    }
}