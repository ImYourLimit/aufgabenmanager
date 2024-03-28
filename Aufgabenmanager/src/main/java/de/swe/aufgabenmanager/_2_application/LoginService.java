package de.swe.aufgabenmanager._2_application;

import de.swe.aufgabenmanager._3_domain.entities.User;
import de.swe.aufgabenmanager._3_domain.entities.UserRepository;

import java.util.List;

public class LoginService {

    UserRepository userRepository;;
    public LoginService(UserRepository userRepository) {
        this.userRepository = userRepository;
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
}
