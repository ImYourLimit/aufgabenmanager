package de.swe.aufgabenmanager._0_plugin.cli;

import de.swe.aufgabenmanager._0_plugin.repositories.GroupRepositoryImpl;
import de.swe.aufgabenmanager._0_plugin.repositories.TaskRepositoryImpl;
import de.swe.aufgabenmanager._0_plugin.repositories.UserRepositoryImpl;
import de.swe.aufgabenmanager._2_application.GroupService;
import de.swe.aufgabenmanager._2_application.TaskService;
import de.swe.aufgabenmanager._3_domain.entities.IGroupRepository;
import de.swe.aufgabenmanager._3_domain.entities.IUserRepository;
import de.swe.aufgabenmanager._3_domain.entities.Task;
import de.swe.aufgabenmanager._3_domain.entities.ITaskRepository;
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
    private ITaskRepository taskRepository;
    private IGroupRepository groupRepository;
    private IUserRepository userRepository;
    private TaskService taskService;
    private Scanner in;
    private CliTaskUtils cliTaskUtils;


    public CliMenu(Long userId, String username) {
        this.userId = userId;
        this.username = username;

        this.taskRepository = new TaskRepositoryImpl();
        this.taskService = new TaskService(taskRepository);

        this.groupRepository = new GroupRepositoryImpl();
        this.userRepository = new UserRepositoryImpl();

        this.in = new Scanner(System.in);
        this.cliTaskUtils = new CliTaskUtils();
    }

    public void start() throws InterruptedException {
        System.out.println("Willkommen " + username + "!");
        System.out.println("Nächste Aufgabe:");
        List<Task> tasks = taskService.getNotCompletedTasksForUser(userId);
        Collections.sort(tasks, Comparator.comparing(Task::getDueDate));
        System.out.println(tasks.get(0).getTitle() + " " + tasks.get(0).getDueDate() + " " + tasks.get(0).getTaskPriority());
        System.out.println();
        System.out.println("Was möchten Sie tun?");
        System.out.println("1 - Aufgaben anzeigen");
        System.out.println("2 - Aufgabe Hinzufügen");
        System.out.println("3 - Gruppen verwalten");
        System.out.println("4 - Beenden");

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
                    CliUtils.clearConsole();
                    CliGroups cliGroups = new CliGroups(groupRepository, userRepository);
                    cliGroups.start();
                    break;
                case 4:
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
        start();
    }

    private void showTasks() throws InterruptedException {
        CliEditTasks cliEditTasks = new CliEditTasks(taskService.getNotCompletedTasksForUser(userId), taskService);
        cliTaskUtils.showTasks(taskService.getNotCompletedTasksForUser(userId));
        System.out.println();
        System.out.println("Was möchten Sie tun?");
        System.out.println("1 - Zurück zum Start");
        System.out.println("2 - Aufgabe bearbeiten");
        System.out.println("3 - Aufgaben Details anzeigen");
        System.out.println("4 - Aufgabe abschließen");
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
                    CliShowTasks cliShowTasks = new CliShowTasks(taskService.getNotCompletedTasksForUser(userId), taskService);
                    cliShowTasks.start();
                    showTasks();
                    break;
                case 4:
                    CliUtils.clearConsole();
                    cliEditTasks.completeTask();
                    CliUtils.clearConsole();
                    showTasks();
                    break;
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
    }
}
