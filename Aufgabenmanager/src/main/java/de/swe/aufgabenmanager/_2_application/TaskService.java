package de.swe.aufgabenmanager._2_application;

import de.swe.aufgabenmanager._3_domain.interfaces.IGroupRepository;
import de.swe.aufgabenmanager._3_domain.entities.Task;
import de.swe.aufgabenmanager._3_domain.interfaces.ITaskRepository;
import de.swe.aufgabenmanager._3_domain.vo.TaskPriority;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class TaskService {
    ITaskRepository taskRepository;
    IGroupRepository groupRepository;

    public TaskService(ITaskRepository taskRepository, IGroupRepository groupRepository) {
        this.taskRepository = taskRepository;
        this.groupRepository = groupRepository;
    }

    public List<Task> getTasksForUser(Long userId) {
        List<Task> userTasks = taskRepository.findByUserId(userId);
        List<Task> groupTasks = taskRepository.findByGroupId(userId);
        return Stream.of(userTasks, groupTasks).flatMap(List::stream).toList();
    }

    public List<Task> getNotCompletedTasksForUser(Long userId) {
        List<Task> userTasks = taskRepository.findByUserId(userId);
        List<Long> groupIds = groupRepository.findGroupIdsByUserId(userId);
        List<Task> groupTasks = new ArrayList<>();
        for (Long groupId : groupIds) {
            groupTasks.addAll(taskRepository.findByGroupId(groupId));
        }
        List<Task> tasks = new java.util.ArrayList<>(Stream.of(userTasks, groupTasks).flatMap(List::stream).toList());
        tasks.removeIf(Task::isCompleted);
        return tasks;
    }

    public void addTask(Long userId, Long groupId, String title, String description, LocalDateTime dueDate, boolean completed, TaskPriority taskPriority) {
        Task task = new Task(generateId(), userId, groupId, title, description, dueDate, completed, taskPriority);
        taskRepository.save(task);
    }

    public void saveTask(Task task) {
        taskRepository.save(task);
    }

    public void deleteTask(Task task) {
        taskRepository.delete(task);
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
