package de.swe.aufgabenmanager._3_domain.entities;

import de.swe.aufgabenmanager._3_domain.vo.TaskPriority;

import java.time.LocalDateTime;

public class Task {
    private Long id;
    private String title;
    private String description;
    private LocalDateTime dueDate;
    private boolean completed;
    private TaskPriority taskPriority;

    public Task (Long id, String title, String description, LocalDateTime dueDate, boolean completed, TaskPriority taskPriority) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
        this.completed = completed;
        this.taskPriority = taskPriority;
    }

}
