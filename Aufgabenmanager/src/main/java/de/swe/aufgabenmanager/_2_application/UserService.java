package de.swe.aufgabenmanager._2_application;

import de.swe.aufgabenmanager._3_domain.entities.IUserRepository;
import de.swe.aufgabenmanager._3_domain.entities.User;

public class UserService {

    IUserRepository userRepository;

    public UserService(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public String getUsername(Long id) {
        User user = userRepository.findById(id);
        return user.getUsername();
    }
}
