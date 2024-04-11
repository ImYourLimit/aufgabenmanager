package de.swe.aufgabenmanager._2_application;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import de.swe.aufgabenmanager._3_domain.entities.Group;
import de.swe.aufgabenmanager._3_domain.entities.User;
import de.swe.aufgabenmanager._3_domain.interfaces.IGroupRepository;
import de.swe.aufgabenmanager._3_domain.interfaces.IUserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class GroupServiceTest {

    @Mock
    private IGroupRepository groupRepository;

    @Mock
    private IUserRepository userRepository;

    @InjectMocks
    private GroupService groupService;

    private Group sampleGroup;
    private User sampleUser;

    @BeforeEach
    void setUp() {
        sampleGroup = new Group(1L, "testgGroup", Arrays.asList(1L, 2L));
        sampleUser = new User(1L, "testUser", "testPassword");
    }

    @Test
    void addGroup_createsAndSavesNewGroup() {
        groupService.addGroup("newGroup", Arrays.asList(1L, 2L));
        verify(groupRepository).save(any(Group.class));
    }

    @Test
    void saveGroup_savesGroup() {
        groupService.saveGroup(sampleGroup);
        verify(groupRepository).save(sampleGroup);
    }

    @Test
    void deleteGroup_deletesGroup() {
        groupService.deleteGroup(sampleGroup);
        verify(groupRepository).delete(sampleGroup);
    }

    @Test
    void getGroupById_returnsGroup() {
        when(groupRepository.findById(1L)).thenReturn(sampleGroup);
        Group result = groupService.getGroupById(1L);
        assertEquals(sampleGroup, result);
    }

    @Test
    void getAllGroups_returnsListOfGroups() {
        when(groupRepository.findAll()).thenReturn(Arrays.asList(sampleGroup));
        List<Group> result = groupService.getAllGroups();
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
    }

    @Test
    void getAllUsers_returnsListOfUsers() {
        when(userRepository.findAll()).thenReturn(Arrays.asList(sampleUser));
        List<User> result = groupService.getAllUsers();
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
    }

    @Test
    void isIdValid_returnsTrueForExistingId() {
        when(groupRepository.findById(1L)).thenReturn(sampleGroup);
        assertTrue(groupService.isIdValid(1L));
    }

    @Test
    void isIdValid_returnsFalseForNonExistingId() {
        when(groupRepository.findById(1L)).thenReturn(null);
        assertFalse(groupService.isIdValid(1L));
    }

    @Test
    void addGroup_generatesUniqueID() {
        groupService.addGroup("UniqueIDGroup", Arrays.asList(3L, 4L));
        verify(groupRepository).save(argThat(group -> group.getId() != null && group.getId() > 0));
    }

    @Test
    void deleteGroup_handlesNullGroup() {
        groupService.deleteGroup(null);
        verify(groupRepository, never()).delete(any(Group.class));
    }
}
