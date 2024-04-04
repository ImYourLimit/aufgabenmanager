package de.swe.aufgabenmanager._3_domain.entities;

import java.util.List;

public interface ITaskRepository {

    public void save(Task task);
    public void delete(Task task);
    public List<Task> findAll();
    List<Task> findByUserId(Long userId);
    List<Task> findByGroupId(Long groupId);
}
