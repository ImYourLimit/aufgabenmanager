package de.swe.aufgabenmanager._3_domain.entities;

public interface UserRepository {
    public void save(User user);
    public void delete(User user);
    public User findById(Long id);
}
