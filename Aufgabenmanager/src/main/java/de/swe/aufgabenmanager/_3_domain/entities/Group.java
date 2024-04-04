package de.swe.aufgabenmanager._3_domain.entities;

import java.util.List;

public class Group {
    private Long id;
    private String name;
    private List<Long> userIds;

    public Group(Long id, String name, List<Long> userIds) {
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

    public List<Long> getUserIds() {
        return userIds;
    }

    public void setUserIds(List<Long> users) {
        this.userIds = users;
    }
}
