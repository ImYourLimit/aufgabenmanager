package de.swe.aufgabenmanager._0_plugin.repositories;

import de.swe.aufgabenmanager._3_domain.entities.User;
import de.swe.aufgabenmanager._3_domain.interfaces.IUserRepository;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UserRepositoryImpl implements IUserRepository {

    private final String CSV_FILE_PATH;
    private static final String[] HEADERS = {"ID", "Username", "Password"};

    public UserRepositoryImpl(String csv_file_path) {
        this.CSV_FILE_PATH = csv_file_path;
    }

    @Override
    public void save(User user) {
        List<User> users = findAll();
        users.removeIf(u -> u.getId().equals(user.getId()));
        users.add(user);
        writeToCsv(users);
    }

    @Override
    public User findById(Long id) {
        List<User> users = findAll();
        for (User u : users) {
            if (u.getId().equals(id)) {
                return u;
            }
        }
        return null;
    }

    @Override
    public List<User> findAll() {
        List<User> users = new ArrayList<>();
        try (FileReader in = new FileReader(CSV_FILE_PATH);
             CSVParser parser = CSVFormat.DEFAULT.withHeader(HEADERS).withFirstRecordAsHeader().parse(in)) {
            for (CSVRecord record : parser) {
                Long id = Long.parseLong(record.get(HEADERS[0]));
                String username = record.get(HEADERS[1]);
                String password = record.get(HEADERS[2]);
                users.add(new User(id, username, password));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return users;
    }

    private void writeToCsv(List<User> users) {
        try (FileWriter out = new FileWriter(CSV_FILE_PATH);
             CSVPrinter printer = new CSVPrinter(out, CSVFormat.DEFAULT.withHeader(HEADERS))) {
            for (User u : users) {
                printer.printRecord(u.getId(), u.getUsername(), u.getPassword());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
