package de.swe.aufgabenmanager._0_plugin.cli;

import de.swe.aufgabenmanager._0_plugin.cli.utils.CliGroupUtils;
import de.swe.aufgabenmanager._0_plugin.cli.utils.CliTaskUtils;
import de.swe.aufgabenmanager._0_plugin.cli.utils.CliUtils;
import de.swe.aufgabenmanager._0_plugin.repositories.GroupRepositoryImpl;
import de.swe.aufgabenmanager._0_plugin.repositories.TaskRepositoryImpl;
import de.swe.aufgabenmanager._0_plugin.repositories.UserRepositoryImpl;
import de.swe.aufgabenmanager._2_application.GroupService;
import de.swe.aufgabenmanager._2_application.TaskService;
import de.swe.aufgabenmanager._2_application.UserService;
import de.swe.aufgabenmanager._3_domain.entities.*;
import de.swe.aufgabenmanager._3_domain.vo.TaskPriority;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

import static java.lang.Thread.sleep;

public class CliMenu {
    private Long userId;
    private String username;
    private ITaskRepository taskRepository;
    private IGroupRepository groupRepository;
    private IUserRepository userRepository;
    private TaskService taskService;
    private GroupService groupService;
    private UserService userService;
    private CliGroups cliGroups;
    private CliTask cliTask;


    public CliMenu(Long userId) {
        this.userId = userId;

        this.taskRepository = new TaskRepositoryImpl();

        this.groupRepository = new GroupRepositoryImpl();
        this.userRepository = new UserRepositoryImpl();

        this.taskService = new TaskService(taskRepository, groupRepository);
        this.groupService = new GroupService(groupRepository, userRepository);
        this.userService = new UserService(userRepository);

        this.cliGroups = new CliGroups(groupRepository, userRepository);
        this.cliTask = new CliTask(userId, taskService.getNotCompletedTasksForUser(userId), taskService, groupService);

        this.username = userService.getUsername(userId);
    }

    public void start() throws InterruptedException {
        System.out.println("Willkommen " + username + "!");
        List<Task> tasks = taskService.getNotCompletedTasksForUser(userId);
        Collections.sort(tasks, Comparator.comparing(Task::getDueDate));
        if (tasks.size() != 0) {
            System.out.println("Nächste Aufgabe:");
            System.out.println(tasks.get(0).getTitle() + " " + tasks.get(0).getDueDate() + " " + tasks.get(0).getTaskPriority());
        }
        System.out.println();
        System.out.println("Was möchten Sie tun?");
        System.out.println("1 - Aufgaben anzeigen");
        System.out.println("2 - Aufgabe Hinzufügen");
        System.out.println("3 - Gruppen verwalten");
        System.out.println();
        System.out.println("0 - Beenden");

        Scanner in = new Scanner(System.in);

        try {
            int a = CliUtils.readInt();
            switch (a) {
                case 0:
                    System.out.println("Auf Wiedersehen!");
                    sleep(1000);
                    System.exit(0);
                    break;
                case 1:
                    CliUtils.clearConsole();
                    cliTask = new CliTask(userId, tasks, taskService, groupService);
                    cliTask.start();
                    break;
                case 2:
                    CliUtils.clearConsole();
                    cliTask.addTask();
                    break;
                case 3:
                    CliUtils.clearConsole();
                    cliGroups.start();
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
}
