package de.swe.aufgabenmanager._0_plugin.repositories;

import de.swe.aufgabenmanager._3_domain.entities.Group;
import de.swe.aufgabenmanager._3_domain.entities.IGroupRepository;
import de.swe.aufgabenmanager._3_domain.entities.Task;
import de.swe.aufgabenmanager._3_domain.entities.User;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GroupRepositoryImpl implements IGroupRepository {

    private static final String CSV_FILE_PATH = "src/main/resources/csv/groups.csv";
    private static final String[] HEADERS = {"ID", "Name", "Users"};


    @Override
    public void save(Group task) {
        List<Group> groups = findAll();
        groups.removeIf(g -> g.getId().equals(task.getId()));
        groups.add(task);
        writeToCsv(groups);
    }

    @Override
    public void delete(Group task) {
        List<Group> groups = findAll();
        groups.removeIf(g -> g.getId().equals(task.getId()));
        writeToCsv(groups);
    }

    @Override
    public Group findById(Long id) {
        List<Group> groups = findAll();
        groups.removeIf(g -> !g.getId().equals(id));
        return null;
    }

    @Override
    public List<Group> findAll() {
        List<Group> groups = new ArrayList<>();
        try (FileReader in = new FileReader(CSV_FILE_PATH);
             CSVParser parser = CSVFormat.DEFAULT.withHeader(HEADERS).withFirstRecordAsHeader().parse(in)) {
            for (CSVRecord record : parser) {
                Long id = Long.parseLong(record.get(HEADERS[0]));
                String name = record.get(HEADERS[1]);
                String usersS = record.get(HEADERS[2]);
                List<int> users = new ArrayList();
                for (String user : usersS.split(",")) {
                    users.add(Integer.parseInt(user));
                }
                groups.add(new Group(id, name, users));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return groups;
    }

    private void writeToCsv(List<Group> groups) {
        try (FileWriter out = new FileWriter(CSV_FILE_PATH);
             CSVPrinter printer = new CSVPrinter(out, CSVFormat.DEFAULT.withHeader(HEADERS))) {
            for (Group g : groups) {
                printer.printRecord(g.getId(), g.getName(), g.getUsers());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
