package de.swe.aufgabenmanager._0_plugin.repositories;

import de.swe.aufgabenmanager._3_domain.entities.Task;
import de.swe.aufgabenmanager._3_domain.interfaces.ITaskRepository;
import de.swe.aufgabenmanager._3_domain.vo.TaskPriority;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TaskRepositoryImpl implements ITaskRepository {

    private final String CSV_FILE_PATH;
    private static final String[] HEADERS = {"ID", "UserId", "GroupId", "Title", "Description", "DueDate", "Completed", "TaskPriority"};

    public TaskRepositoryImpl(String csv_file_path) {
        this.CSV_FILE_PATH = csv_file_path;
    }

    @Override
    public void save(Task task) {
        List<Task> tasks = findAll();
        tasks.removeIf(t -> t.getId().equals(task.getId()));
        tasks.add(task);
        writeToCsv(tasks);
    }

    @Override
    public void delete(Task task) {
        List<Task> tasks = findAll();
        tasks.removeIf(t -> t.getId().equals(task.getId()));
        writeToCsv(tasks);
    }

    @Override
    public List<Task> findAll() {
        List<Task> tasks = new ArrayList<>();
        try (FileReader in = new FileReader(CSV_FILE_PATH);
             CSVParser parser = CSVFormat.DEFAULT.withHeader(HEADERS).withFirstRecordAsHeader().parse(in)) {
            for (CSVRecord record : parser) {
                Long id = Long.parseLong(record.get(HEADERS[0]));
                Long userId = Long.parseLong(record.get(HEADERS[1]));
                Long groupId = Long.parseLong(record.get(HEADERS[2]));
                String title = record.get(HEADERS[3]);
                String description = record.get(HEADERS[4]);
                LocalDateTime dueDate = LocalDateTime.parse(record.get(HEADERS[5]));
                boolean completed = Boolean.parseBoolean(record.get(HEADERS[6]));
                TaskPriority taskPriority = TaskPriority.valueOf(record.get(HEADERS[7]));
                tasks.add(new Task(id, userId, groupId, title, description, dueDate, completed, taskPriority));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return tasks;
    }

    @Override
    public List<Task> findByUserId(Long userId) {
        List<Task> tasks = findAll();
        tasks.removeIf(t -> !t.getUserId().equals(userId));
        return tasks;
    }

    @Override
    public List<Task> findByGroupId(Long groupId) {
        List<Task> tasks = findAll();
        tasks.removeIf(t -> !t.getGroupId().equals(groupId));
        return tasks;
    }

    private void writeToCsv(List<Task> tasks) {
        try (FileWriter out = new FileWriter(CSV_FILE_PATH);
             CSVPrinter printer = new CSVPrinter(out, CSVFormat.DEFAULT.withHeader(HEADERS))) {
            for (Task t : tasks) {
                printer.printRecord(t.getId(), t.getUserId(), t.getGroupId(), t.getTitle(), t.getDescription(), t.getDueDate(), t.isCompleted(), t.getTaskPriority());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
