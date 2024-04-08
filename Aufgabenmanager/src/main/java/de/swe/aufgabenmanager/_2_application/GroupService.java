package de.swe.aufgabenmanager._2_application;

import de.swe.aufgabenmanager._3_domain.entities.Group;
import de.swe.aufgabenmanager._3_domain.interfaces.IGroupRepository;
import de.swe.aufgabenmanager._3_domain.interfaces.IUserRepository;
import de.swe.aufgabenmanager._3_domain.entities.User;

import java.util.List;

public class GroupService {

    private IGroupRepository groupRepository;
    private IUserRepository userRepository;

    public GroupService(IGroupRepository groupRepository, IUserRepository userRepository) {
        this.groupRepository = groupRepository;
        this.userRepository = userRepository;
    }

    public void addGroup(String name, List<Long> userIds) {
        Group group = new Group(generateId(), name, userIds);
        groupRepository.save(group);
    }

    public void saveGroup(Group group) {
        groupRepository.save(group);
    }

    public void deleteGroup(Group group) {
        groupRepository.delete(group);
    }

    public Group getGroupById(Long id) {
        return groupRepository.findById(id);
    }

    public List<Group> getAllGroups() {
        return groupRepository.findAll();
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public boolean isIdValid(Long id) {
        return groupRepository.findById(id) != null;
    }
    private Long generateId() {
        List<Group> groups = groupRepository.findAll();
        Long maxId = 0L;
        for (Group g : groups) {
            if (g.getId() > maxId) {
                maxId = g.getId();
            }
        }
        return maxId + 1L;
    }
}
