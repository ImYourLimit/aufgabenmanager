package de.swe.aufgabenmanager._3_domain.entities;

import java.util.List;

public class Group {
    private Long id;
    private String name;
    private List<Integer> userIds;

    public Group(Long id, String name, List<Integer> userIds) {
        this.id = id;
        this.name = name;
        this.userIds = userIds;
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

    public List<Integer> getUserIds() {
        return userIds;
    }

    public void setUserIds(List<Integer> users) {
        this.userIds = users;
    }
}
