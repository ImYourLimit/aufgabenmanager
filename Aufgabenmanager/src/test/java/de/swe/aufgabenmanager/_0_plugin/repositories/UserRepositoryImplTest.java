package de.swe.aufgabenmanager._0_plugin.repositories;

import de.swe.aufgabenmanager._3_domain.entities.User;
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

class UserRepositoryImplTest {

    private UserRepositoryImpl userRepository;

    @TempDir
    File tempDir;

    private static final String[] HEADERS = {"ID", "Username", "Password"};

    @BeforeEach
    void setUp() throws IOException {

        MockitoAnnotations.initMocks(this);

        String tempCsvFilePath = tempDir.getAbsolutePath() + "/users.csv";
        Path pathToCsv = Path.of(tempCsvFilePath);
        Files.createFile(pathToCsv);
        Files.writeString(pathToCsv, String.join(",", HEADERS) + "\n");

        Files.writeString(pathToCsv, "1,Test User,TestPassword\n", StandardOpenOption.APPEND);

        userRepository = new UserRepositoryImpl(tempCsvFilePath);
    }

    @Test
    void save() {
        User user = new User(999L, "Test User", "TestPassword");

        userRepository.save(user);

        User retreivedUser = userRepository.findById(999L);
        assertEquals(retreivedUser.getId(), 999L);
    }

    @Test
    void findById() {
        User retreivedUser = userRepository.findById(1L);

        assertNotNull(retreivedUser);
        assertEquals(retreivedUser.getId(), 1L);
    }

    @Test
    void findAll() {
        List<User> userList = userRepository.findAll();

        assertFalse(userList.isEmpty());
    }
}