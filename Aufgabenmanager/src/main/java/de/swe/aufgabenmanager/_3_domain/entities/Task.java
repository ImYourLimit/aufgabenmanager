package de.swe.aufgabenmanager._3_domain.entities;

import de.swe.aufgabenmanager._3_domain.vo.TaskPriority;

import java.time.LocalDateTime;

public class Task {
    private Long id;
    private Long userId;
    private Long groupId;
    private String title;
    private String description;
    private LocalDateTime dueDate;
    private boolean completed;
    private TaskPriority taskPriority;

    public Task (Long id, Long userId, Long groupId, String title, String description, LocalDateTime dueDate, boolean completed, TaskPriority taskPriority) {
        this.id = id;
        this.userId = userId;
        this.groupId = groupId;
        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
        this.completed = completed;
        this.taskPriority = taskPriority;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public Long getGroupId() {
        return groupId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDateTime dueDate) {
        this.dueDate = dueDate;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public TaskPriority getTaskPriority() {
        return taskPriority;
    }

    public void setTaskPriority(TaskPriority taskPriority) {
        this.taskPriority = taskPriority;
    }
}
