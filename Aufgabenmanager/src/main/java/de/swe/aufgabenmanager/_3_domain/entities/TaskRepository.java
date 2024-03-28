package de.swe.aufgabenmanager._3_domain.entities;

import java.util.List;

public interface TaskRepository {

    public void save(Task task);
    public void delete(Task task);
    public Task findById(Long id);
    public List<Task> findAll();
}
