package de.swe.aufgabenmanager._2_application;

import de.swe.aufgabenmanager._3_domain.entities.User;
import de.swe.aufgabenmanager._3_domain.entities.IUserRepository;
import java.util.List;

public class RegisterService {

    IUserRepository IUserRepository;
    public RegisterService(IUserRepository IUserRepository) {
        this.IUserRepository = IUserRepository;
    }
    public boolean usernameTaken(String username) {
        List<User> users = IUserRepository.findAll();
        for (User u : users) {
            if (u.getUsername().equals(username)) {
                return true;
            }
        }
        return false;
    }

    public void register(String username, String password) {
        User user = new User(generateId(), username, password);
        IUserRepository.save(user);
    }

    private Long generateId() {
        List<User> users = IUserRepository.findAll();
        Long maxId = 0L;
        for (User u : users) {
            if (u.getId() > maxId) {
                maxId = u.getId();
            }
        }
        return maxId + 1L;
    }
}
