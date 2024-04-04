package de.swe.aufgabenmanager._2_application;

import de.swe.aufgabenmanager._3_domain.entities.Task;
import de.swe.aufgabenmanager._3_domain.entities.ITaskRepository;
import de.swe.aufgabenmanager._3_domain.vo.TaskPriority;

import java.time.LocalDateTime;
import java.util.List;

public class TaskService {
    ITaskRepository ITaskRepository;

    public TaskService(ITaskRepository ITaskRepository) {
        this.ITaskRepository = ITaskRepository;
    }

    public List<Task> getTasksForUser(Long userId) {
        return ITaskRepository.findByUserId(userId);
    }

    public List<Task> getNotCompletedTasksForUser(Long userId) {
        List<Task> tasks = ITaskRepository.findByUserId(userId);
        tasks.removeIf(Task::isCompleted);
        return tasks;
    }

    public void addTask(Long userId, String title, String description, LocalDateTime dueDate, boolean completed, TaskPriority taskPriority) {
        Task task = new Task(generateId(), userId, title, description, dueDate, completed, taskPriority);
        ITaskRepository.save(task);
    }

    public void saveTask(Task task) {
        ITaskRepository.save(task);
    }

    public void deleteTask(Task task) {
        ITaskRepository.delete(task);
    }

    private Long generateId() {
        List<Task> tasks = ITaskRepository.findAll();
        Long maxId = 0L;
        for (Task t : tasks) {
            if (t.getId() > maxId) {
                maxId = t.getId();
            }
        }
        return maxId + 1L;
    }
}
