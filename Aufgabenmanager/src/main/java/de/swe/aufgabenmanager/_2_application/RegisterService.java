package de.swe.aufgabenmanager._2_application;

import de.swe.aufgabenmanager._0_plugin.repositories.CSVUserRepository;
import de.swe.aufgabenmanager._3_domain.entities.User;
import de.swe.aufgabenmanager._3_domain.entities.UserRepository;
import java.util.List;

public class RegisterService {

    UserRepository userRepository;
    public RegisterService(UserRepository userRepository) {
        this.userRepository = userRepository;
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
        User user = new User(1L, username, password);
        userRepository.save(user);
    }
}
