package de.swe.aufgabenmanager._0_plugin.repositories;

import de.swe.aufgabenmanager._3_domain.entities.Task;
import de.swe.aufgabenmanager._3_domain.entities.TaskRepository;
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

public class CSVTaskRepository implements TaskRepository {

    private static final String CSV_FILE_PATH = "src/main/resources/csv/tasks.csv";
    private static final String[] HEADERS = {"ID", "Title", "Description", "DueDate", "Completed", "TaskPriority"};

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
    public Task findById(Long id) {
        List<Task> tasks = findAll();
        for (Task t : tasks) {
            if (t.getId().equals(id)) {
                return t;
            }
        }
        return null;
    }

    @Override
    public List<Task> findAll() {
        List<Task> tasks = new ArrayList<>();
        try (FileReader in = new FileReader(CSV_FILE_PATH);
             CSVParser parser = CSVFormat.DEFAULT.withHeader(HEADERS).withFirstRecordAsHeader().parse(in)) {
            for (CSVRecord record : parser) {
                Long id = Long.parseLong(record.get("ID"));
                String title = record.get("Title");
                String description = record.get("Description");
                LocalDateTime dueDate = LocalDateTime.parse(record.get("DueDate"));
                boolean completed = Boolean.parseBoolean(record.get("Completed"));
                TaskPriority taskPriority = TaskPriority.valueOf(record.get("TaskPriority"));
                tasks.add(new Task(id, title, description, dueDate, completed, taskPriority));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return tasks;
    }

    private void writeToCsv(List<Task> tasks) {
        try (FileWriter out = new FileWriter(CSV_FILE_PATH);
             CSVPrinter printer = new CSVPrinter(out, CSVFormat.DEFAULT.withHeader(HEADERS))) {
            for (Task t : tasks) {
                printer.printRecord(t.getId(), t.getTitle(), t.getDescription(), t.getDueDate(), t.isCompleted(), t.getTaskPriority());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
