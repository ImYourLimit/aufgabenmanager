package de.swe.aufgabenmanager;

import de.swe.aufgabenmanager._0_plugin.repositories.CSVTaskRepository;
import de.swe.aufgabenmanager._3_domain.entities.Task;
import de.swe.aufgabenmanager._3_domain.entities.TaskRepository;
import de.swe.aufgabenmanager._3_domain.vo.TaskPriority;

import java.time.LocalDateTime;

public class Main {
    public static void main(String[] args) {
        Task task = new Task(1L, "Test Titel", "Test Beschreibung", LocalDateTime.now(), false, TaskPriority.HIGH);
        TaskRepository taskRepository = new CSVTaskRepository();
        taskRepository.save(task);

        Task foundTask = taskRepository.findById(1L);
        System.out.println(foundTask.getTaskPriority());
    }
}