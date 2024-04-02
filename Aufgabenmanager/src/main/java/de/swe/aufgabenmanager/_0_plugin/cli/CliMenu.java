package de.swe.aufgabenmanager._0_plugin.cli;

import de.swe.aufgabenmanager._0_plugin.repositories.CSVTaskRepository;
import de.swe.aufgabenmanager._2_application.TaskService;
import de.swe.aufgabenmanager._3_domain.entities.Task;
import de.swe.aufgabenmanager._3_domain.entities.TaskRepository;
import de.swe.aufgabenmanager._3_domain.vo.TaskPriority;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

import static java.lang.Thread.sleep;

public class CliMenu {
    private Long userId;
    private String username; //username über userId holen, nicht im konstuktor parameter
    private TaskRepository taskRepository;
    private TaskService taskService;
    private Scanner in;
    private CliTaskUtils cliTaskUtils;


    public CliMenu(Long userId, String username) {
        this.userId = userId;
        this.username = username;
        taskRepository = new CSVTaskRepository();
        taskService = new TaskService(taskRepository);
        this.in = new Scanner(System.in);
        this.cliTaskUtils = new CliTaskUtils();
    }

    public void start() throws InterruptedException {
        System.out.println("Willkommen " + username + "!");
        System.out.println("Wichtige aktuelle Aufgabe:");
        List<Task> tasks = taskService.getNotCompletedTasksForUser(userId);
        Collections.sort(tasks, Comparator.comparing(Task::getDueDate).reversed());
        System.out.println(tasks.get(0).getTitle() + " " + tasks.get(0).getDueDate() + " " + tasks.get(0).getTaskPriority());
        System.out.println();
        System.out.println("Was möchten Sie tun?");
        System.out.println("1 - Aufgaben anzeigen");
        System.out.println("2 - Aufgabe Hinzufügen");
        System.out.println("3 - Beenden");

        Scanner in = new Scanner(System.in);

        try {
            int a = CliUtils.readInt();
            switch (a) {
                case 1:
                    CliUtils.clearConsole();
                    showTasks();
                    break;
                case 2:
                    CliUtils.clearConsole();
                    addTask();
                    break;
                case 3:
                    System.out.println("Auf Wiedersehen!");
                    sleep(1000);
                    System.exit(0);
                    break;
                default:
                    System.out.println("Fehler: Bitte geben Sie eine gültige Nummer ein.");
                    sleep(1000);
                    CliUtils.clearConsole();
                    start();
            }
        } catch (Exception e) {
            in.nextLine();
            System.out.println("Fehler: Bitte geben Sie eine Nummer ein.");
            sleep(1000);
            CliUtils.clearConsole();
            start();
        }
    }

    private void showTasks() throws InterruptedException {
        CliEditTasks cliEditTasks = new CliEditTasks(taskService.getNotCompletedTasksForUser(userId), taskService);
        cliEditTasks.showTasks();
        System.out.println();
        System.out.println("Was möchten Sie tun?");
        System.out.println("1 - Zurück zum Start");
        System.out.println("2 - Aufgabe bearbeiten");
        System.out.println("3 - Aufgabe abschließen");
        Scanner in = new Scanner(System.in);
        try {
            int a = CliUtils.readInt();
            switch (a) {
                case 1:
                    CliUtils.clearConsole();
                    start();
                    break;
                case 2:
                    CliUtils.clearConsole();
                    cliEditTasks.editTasks();
                    CliUtils.clearConsole();
                    showTasks();
                    break;
                case 3:
                    CliUtils.clearConsole();
                    cliEditTasks.completeTask();
                    CliUtils.clearConsole();
                    showTasks();
                default:
                    System.out.println("Fehler: Bitte geben Sie eine gültige Nummer ein.");
                    sleep(1000);
                    CliUtils.clearConsole();
                    showTasks();
            }
        } catch (Exception e) {
            in.nextLine();
            System.out.println("Fehler: Bitte geben Sie eine Nummer ein.");
            sleep(1000);
            CliUtils.clearConsole();
            showTasks();
        }
    }

    private void addTask() throws InterruptedException {
        System.out.println("Neue Aufgabe erstellen.");
        String title = cliTaskUtils.enterTitle();
        LocalDateTime dueDate = cliTaskUtils.enterDate();
        String description = cliTaskUtils.enterDescription();
        TaskPriority taskPriority = cliTaskUtils.enterTaskPriority();
        taskService.addTask(userId, title, description, dueDate, false, taskPriority);
        System.out.println("Aufgabe erfolgreich erstellt.");
        sleep(1000);
        CliUtils.clearConsole();
        start();
    }
}
