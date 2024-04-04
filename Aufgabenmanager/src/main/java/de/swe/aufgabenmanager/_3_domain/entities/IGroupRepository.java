package de.swe.aufgabenmanager._3_domain.entities;

import java.util.List;

public interface IGroupRepository {

    public void save(Group task);
    public void delete(Group task);
    public Group findById(Long id);
    public List<Long> findGroupIdsByUserId(Long userId);
    public List<Group> findAll();
}
