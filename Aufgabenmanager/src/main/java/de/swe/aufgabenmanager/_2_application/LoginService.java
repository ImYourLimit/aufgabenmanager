package de.swe.aufgabenmanager._2_application;

import de.swe.aufgabenmanager._3_domain.entities.User;
import de.swe.aufgabenmanager._3_domain.entities.IUserRepository;

import java.util.List;

public class LoginService {

    IUserRepository IUserRepository;;
    public LoginService(IUserRepository IUserRepository) {
        this.IUserRepository = IUserRepository;
    }

    public boolean login(String username, String password) {
        List<User> users = IUserRepository.findAll();
        for (User u : users) {
            if (u.getUsername().equals(username) && u.getPassword().equals(password)) {
                return true;
            }
        }
        return false;
    }

    public Long getUserId(String username) {
        List<User> users = IUserRepository.findAll();
        for (User u : users) {
            if (u.getUsername().equals(username)) {
                return u.getId();
            }
        }
        return null;
    }
}
