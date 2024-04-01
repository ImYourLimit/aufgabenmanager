package de.swe.aufgabenmanager._2_application;

import de.swe.aufgabenmanager._3_domain.entities.Task;
import de.swe.aufgabenmanager._3_domain.entities.TaskRepository;
import de.swe.aufgabenmanager._3_domain.vo.TaskPriority;

import java.time.LocalDateTime;
import java.util.List;

public class TaskService {
    TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public List<Task> getTasksForUser(Long userId) {
        return taskRepository.findByUserId(userId);
    }

    public void addTask(Long userId, String title, String description, LocalDateTime dueDate, boolean completed, TaskPriority taskPriority) {
        Task task = new Task(generateId(), userId, title, description, dueDate, completed, taskPriority);
        taskRepository.save(task);
    }

    public void saveTask(Task task) {
        taskRepository.save(task);
    }

    private Long generateId() {
        List<Task> tasks = taskRepository.findAll();
        Long maxId = 0L;
        for (Task t : tasks) {
            if (t.getId() > maxId) {
                maxId = t.getId();
            }
        }
        return maxId + 1L;
    }
}
