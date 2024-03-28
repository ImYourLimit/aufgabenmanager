package de.swe.aufgabenmanager._3_domain.entities;

import java.util.List;

public interface UserRepository {
    public void save(User user);
    public void delete(User user);
    public User findById(Long id);
    public List<User> findAll();
}
