package de.swe.aufgabenmanager._2_application;

import de.swe.aufgabenmanager._3_domain.interfaces.IUserRepository;
import de.swe.aufgabenmanager._3_domain.entities.User;

import java.util.List;

public class UserService {

    IUserRepository userRepository;

    public UserService(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public String getUsername(Long id) {
        User user = userRepository.findById(id);
        return user.getUsername();
    }

    public boolean usernameTaken(String username) {
        List<User> users = userRepository.findAll();
        for (User u : users) {
            if (u.getUsername().equals(username)) {
                return true;
            }
        }
        return false;
    }

    public void register(String username, String password) {
        User user = new User(generateId(), username, password);
        userRepository.save(user);
    }

    public boolean login(String username, String password) {
        List<User> users = userRepository.findAll();
        for (User u : users) {
            if (u.getUsername().equals(username) && u.getPassword().equals(password)) {
                return true;
            }
        }
        return false;
    }

    public Long getUserId(String username) {
        List<User> users = userRepository.findAll();
        for (User u : users) {
            if (u.getUsername().equals(username)) {
                return u.getId();
            }
        }
        return null;
    }

    private Long generateId() {
        List<User> users = userRepository.findAll();
        Long maxId = 0L;
        for (User u : users) {
            if (u.getId() > maxId) {
                maxId = u.getId();
            }
        }
        return maxId + 1L;
    }
}
