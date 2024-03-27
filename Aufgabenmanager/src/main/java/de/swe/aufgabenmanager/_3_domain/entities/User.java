package de.swe.aufgabenmanager._3_domain.entities;

public class User {
    Long id;
    private String username;
    private String password;

    public User(Long id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }
}
