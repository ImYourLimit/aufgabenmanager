package de.swe.aufgabenmanager._0_plugin.cli;

import de.swe.aufgabenmanager._0_plugin.cli.utils.CliTaskUtils;
import de.swe.aufgabenmanager._0_plugin.cli.utils.CliUtils;
import de.swe.aufgabenmanager._2_application.TaskService;
import de.swe.aufgabenmanager._3_domain.entities.Task;

import java.util.List;
import java.util.Scanner;

public class CliEditTask {

    private TaskService taskService;
    private List<Task> tasks;
    private CliTaskUtils cliTaskUtils;

    public CliEditTask(TaskService taskService, List<Task> tasks, CliTaskUtils cliTaskUtils) {
        this.taskService = taskService;
        this.tasks = tasks;
        this.cliTaskUtils = cliTaskUtils;
    }

    public void start() {
        editTasksMenu();
    }

    private void editTasksMenu() {
        cliTaskUtils.showTasks(tasks);
        System.out.println();
        System.out.println("0 - Zurück");
        System.out.println("Welche Aufgabe möchten Sie bearbeiten?");
        int taskNumber = CliUtils.readInt() - 1;
        if (taskNumber == -1) {
            return;
        }
        editTask(taskNumber);
    }

    private void editTask(int taskNumber) {
        Task task = tasks.get(taskNumber);
        System.out.println("Was möchten Sie bearbeiten?");
        System.out.println("1 - Titel");
        System.out.println("2 - Beschreibung");
        System.out.println("3 - Fälligkeitsdatum");
        System.out.println("4 - Priorität");
        System.out.println("5 - Löschen");
        System.out.println();
        System.out.println("0 - Zurück");
        int a = CliUtils.readInt();
        switch (a) {
            case 0:
                break;
            case 1:
                System.out.println("Geben Sie den neuen Titel ein:");
                String newTitle = cliTaskUtils.enterTitle();
                task.setTitle(newTitle);
                System.out.println("Titel geändert.");
                break;
            case 2:
                System.out.println("Geben Sie die neue Beschreibung ein:");
                String newDescription = cliTaskUtils.enterDescription();
                task.setDescription(newDescription);
                System.out.println("Beschreibung geändert.");
                break;
            case 3:
                System.out.println("Geben Sie das neue Fälligkeitsdatum ein:");
                task.setDueDate(cliTaskUtils.enterDueDate());
                System.out.println("Fälligkeitsdatum geändert.");
                break;
            case 4:
                System.out.println("Geben Sie die neue Priorität ein:");
                task.setTaskPriority(cliTaskUtils.enterTaskPriority());
                System.out.println("Priorität geändert.");
                break;
            case 5:
                taskService.deleteTask(task);
                System.out.println("Aufgabe gelöscht.");
                break;
            default:
                System.out.println("Fehler: Bitte geben Sie eine gültige Nummer ein.");
                editTask(taskNumber);
        }
        CliUtils.sleepFor(1000);
        taskService.saveTask(task);
        tasks = taskService.getTasksForUser(task.getUserId());
        editTasksMenu();
    }
}
