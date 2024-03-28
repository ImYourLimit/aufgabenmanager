package de.swe.aufgabenmanager._0_plugin.cli;

import de.swe.aufgabenmanager._0_plugin.repositories.CSVTaskRepository;
import de.swe.aufgabenmanager._2_application.TaskService;
import de.swe.aufgabenmanager._3_domain.entities.Task;
import de.swe.aufgabenmanager._3_domain.entities.TaskRepository;

import java.util.List;
import java.util.Scanner;

import static java.lang.Thread.sleep;

public class CliMenu {
    private Long userId;
    private String username;
    private TaskRepository taskRepository;
    private TaskService taskService;


    public CliMenu(Long userId, String username) {
        this.userId = userId;
        this.username = username;
        taskRepository = new CSVTaskRepository();
        taskService = new TaskService(taskRepository);
    }

    public void start() throws InterruptedException {
        System.out.println("Willkommen " + username + "!");
        System.out.println("Was möchten Sie tun?");
        System.out.println("1 - Aufgaben anzeigen");
        System.out.println("2 - Aufgabe Hinzufügen");
        System.out.println("3 - Beenden");

        Scanner in = new Scanner(System.in);

        try {
            int a = in.nextInt();
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

    private void showTasks() {
        List<Task> tasks = taskService.getTasksForUser(userId);
        System.out.println("Ihre Aufgaben:");
        int i = 0;
        for (Task task : tasks) {
            System.out.println(i + " - " + task.getTitle());
            i++;
        }
    }

    private void addTask() {
        System.out.println("Sie können jetzt eine neue Aufgabe hinzufügen.");
    }
}
