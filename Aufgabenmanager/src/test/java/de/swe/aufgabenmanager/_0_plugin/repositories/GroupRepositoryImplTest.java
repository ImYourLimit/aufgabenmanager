package de.swe.aufgabenmanager._0_plugin.repositories;

import de.swe.aufgabenmanager._3_domain.entities.Group;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.MockitoAnnotations;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GroupRepositoryImplTest {

    private GroupRepositoryImpl groupRepository;
    private static final String[] HEADERS = {"ID", "Name", "UserIds"};

    @TempDir
    File tempDir;

    @BeforeEach
    void setUp() throws IOException {

        String tempCsvFilePath = tempDir.getAbsolutePath() + "/groups.csv";
        Path pathToCsv = Path.of(tempCsvFilePath);
        Files.createFile(pathToCsv);
        Files.writeString(pathToCsv, String.join(",", HEADERS) + "\n");

        Files.writeString(pathToCsv, "1,InitialGroup,[1,2]\n", StandardOpenOption.APPEND);

        groupRepository = new GroupRepositoryImpl(tempCsvFilePath);
    }

    @Test
    void save() {
        Group newGroup = new Group(999L, "TestGroup", List.of(1L, 2L));
        groupRepository.save(newGroup);

        Group retrievedGroup = groupRepository.findById(999L);

        assertNotNull(retrievedGroup);
        assertEquals("TestGroup", retrievedGroup.getName());
    }

    @Test
    void delete() {
        Group newGroup = new Group(999L, "TestGroup", List.of(1L, 2L));
        groupRepository.save(newGroup);

        groupRepository.delete(newGroup);

        assertNull(groupRepository.findById(999L));
    }

    @Test
    void findById() {
        Group group = groupRepository.findById(1L);

        assertNotNull(group);

        assertEquals(1L, group.getId());
    }

    @Test
    void findGroupIdsByUserId() {
        List<Long> groupIds = groupRepository.findGroupIdsByUserId(1L);

        assertFalse(groupIds.isEmpty());
        assertTrue(groupIds.contains(1L));
    }

    @Test
    void findAll() {
        List<Group> groups = groupRepository.findAll();
        assertFalse(groups.isEmpty());
    }
}