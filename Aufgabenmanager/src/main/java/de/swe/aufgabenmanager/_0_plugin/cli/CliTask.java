package de.swe.aufgabenmanager._0_plugin.cli;

import de.swe.aufgabenmanager._0_plugin.cli.utils.CliGroupUtils;
import de.swe.aufgabenmanager._0_plugin.cli.utils.CliTaskUtils;
import de.swe.aufgabenmanager._0_plugin.cli.utils.CliUtils;
import de.swe.aufgabenmanager._2_application.GroupService;
import de.swe.aufgabenmanager._2_application.TaskService;
import de.swe.aufgabenmanager._3_domain.entities.Task;
import de.swe.aufgabenmanager._3_domain.vo.TaskPriority;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

public class CliTask {

    private Long userId;
    private List<Task> tasks;
    private Scanner in;
    private CliTaskUtils cliTaskUtils;
    private TaskService taskService;
    private GroupService groupService;
    private CliGroupUtils cliGroupUtils;

    public CliTask(Long userId, List<Task> tasks, TaskService taskService, GroupService groupService) {
        this.userId = userId;
        this.tasks = tasks;
        this.taskService = taskService;
        this.groupService = groupService;
        this.in = new Scanner(System.in);
        this.cliTaskUtils = new CliTaskUtils();

        this.cliGroupUtils = new CliGroupUtils(groupService);
    }

    public void start() {
        cliTaskUtils.showTasks(taskService.getNotCompletedTasksForUser(userId));
        System.out.println();
        System.out.println("Was möchten Sie tun?");
        System.out.println("1 - Aufgabe bearbeiten");
        System.out.println("2 - Aufgaben Details anzeigen");
        System.out.println("3 - Aufgabe abschließen");
        System.out.println();
        System.out.println("0 - Zurück zum Start");
        Scanner in = new Scanner(System.in);
        try {
            int a = readInt();
            switch (a) {
                case 0:
                    CliUtils.clearConsole();
                    return;
                case 1:
                    CliUtils.clearConsole();
                    CliEditTask cliEditTask = new CliEditTask(taskService, tasks, cliTaskUtils);
                    cliEditTask.start();
                    CliUtils.clearConsole();
                    start();
                    break;
                case 2:
                    CliUtils.clearConsole();
                    showTasks();
                    start();
                    break;
                case 3:
                    CliUtils.clearConsole();
                    completeTask();
                    CliUtils.clearConsole();
                    start();
                    break;
                default:
                    System.out.println("Fehler: Bitte geben Sie eine gültige Nummer ein.");
                    CliUtils.sleepFor(1000);
                    CliUtils.clearConsole();
                    start();
            }
        } catch (Exception e) {
            in.nextLine();
            System.out.println("Fehler: Bitte geben Sie eine Nummer ein.");
            CliUtils.sleepFor(1000);
            CliUtils.clearConsole();
            start();
        }
    }

    private void showTasks() {
        cliTaskUtils.showTasks(tasks);
        System.out.println();
        System.out.println("Welche Aufgabe möchten sie anzeigen lassen?");
        System.out.println("0 - Zurück");

        int taskNumber = readInt() - 1;
        if (taskNumber == -1) {
            return;
        }
        displayTaskDetails(taskNumber);
    }

    private void displayTaskDetails(int taskNumber) {
        Task task = tasks.get(taskNumber);
        System.out.println("Titel: " + task.getTitle());
        System.out.println("Beschreibung: " + task.getDescription());
        System.out.println("Fälligkeitsdatum: " + task.getDueDate());
        System.out.println("Priorität: " + task.getTaskPriority());

        System.out.println();
        System.out.println("0 - Zurück");

        int a = readInt();
        switch (a) {
            case 0:
                return;
            default:
                System.out.println("Fehler: Bitte geben Sie eine gültige Nummer ein.");
                displayTaskDetails(taskNumber);
        }
    }

    private void completeTask() {
        cliTaskUtils.showTasks(tasks);
        System.out.println();
        System.out.println("0 - Zurück");
        System.out.println("Welche Aufgabe möchten Sie abschließen?");
        int taskNumber = readInt() - 1;
        if (taskNumber == -1) {
            return;
        }
        Task task = tasks.get(taskNumber);
        task.setCompleted(true);
        taskService.saveTask(task);
        tasks = taskService.getTasksForUser(task.getUserId());
        System.out.println("Aufgabe abgeschlossen.");
        CliUtils.sleepFor(1000);
    }

    public void addTask() {
        System.out.println("Neue Aufgabe erstellen.");
        boolean isGroupTask = cliTaskUtils.enterIsGroupTask();
        Long groupId = -1L;
        if (isGroupTask) {
            cliGroupUtils.showGroups(groupService.getAllGroups());
            System.out.println();
            System.out.println("Geben sie die ID der Gruppe ein, der die Aufgabe hinzugefügt werden soll:");
            groupId = (long) cliGroupUtils.enterGroupId();
        }
        String title = cliTaskUtils.enterTitle();
        LocalDateTime dueDate = cliTaskUtils.enterDueDate();
        String description = cliTaskUtils.enterDescription();
        TaskPriority taskPriority = cliTaskUtils.enterTaskPriority();
        if (isGroupTask) {
            taskService.addTask(-1L, groupId, title, description, dueDate, false, taskPriority);
        } else {
            taskService.addTask(userId, groupId, title, description, dueDate, false, taskPriority);
        }
        System.out.println("Aufgabe erfolgreich erstellt.");
        CliUtils.sleepFor(1000);
        CliUtils.clearConsole();
    }

    private int readInt() {
        Scanner in = new Scanner(System.in);
        try {
            int a = in.nextInt();
            return a;
        } catch (Exception e) {
            in.nextLine();
            System.out.println("Fehler: Bitte geben Sie eine Nummer ein.");
            readInt();
        }
        return 0;
    }

}
