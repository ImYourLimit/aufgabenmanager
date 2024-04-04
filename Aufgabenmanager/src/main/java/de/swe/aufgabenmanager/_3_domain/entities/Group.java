package de.swe.aufgabenmanager._3_domain.entities;

import java.util.List;

public class Group {
    private Long id;
    private String name;
    private List<int> users;

    public Group(Long id, String name, List<int> users) {
        this.id = id;
        this.name = name;
        this.users = users;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<int> getUsers() {
        return users;
    }

    public void setUsers(List<int> users) {
        this.users = users;
    }
}
