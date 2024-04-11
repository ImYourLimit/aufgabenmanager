package de.swe.aufgabenmanager._0_plugin.repositories;

import de.swe.aufgabenmanager._3_domain.entities.Group;
import de.swe.aufgabenmanager._3_domain.interfaces.IGroupRepository;
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

    private final String CSV_FILE_PATH;
    private static final String[] HEADERS = {"ID", "Name", "UserIds"};

    public GroupRepositoryImpl(String csvFilePath) {
        this.CSV_FILE_PATH = csvFilePath;
    }
    @Override
    public void save(Group group) {
        List<Group> groups = findAll();
        groups.removeIf(g -> g.getId().equals(group.getId()));
        groups.add(group);
        writeToCsv(groups);
    }

    @Override
    public void delete(Group group) {
        List<Group> groups = findAll();
        groups.removeIf(g -> g.getId().equals(group.getId()));
        writeToCsv(groups);
    }

    @Override
    public Group findById(Long id) {
        List<Group> groups = findAll();
        groups.removeIf(g -> !g.getId().equals(id));
        return groups.isEmpty() ? null : groups.get(0);
    }

    @Override
    public List<Long> findGroupIdsByUserId(Long userId) {
        List<Group> groups = findAll();
        List<Long> groupIds = new ArrayList<>();
        for (Group g : groups) {
            if (g.getUserIds().contains(userId)) {
                groupIds.add(g.getId());
            }
        }
        return groupIds;
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
                List<Long> users = new ArrayList<>();
                usersS = usersS.replace("[", "");
                usersS = usersS.replace("]", "");
                for (String user : usersS.split(",")) {
                    user = user.replace(" ", "");
                    users.add(Long.parseLong(user));
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
                printer.printRecord(g.getId(), g.getName(), g.getUserIds());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
