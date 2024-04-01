package de.swe.aufgabenmanager._0_plugin.cli;

import de.swe.aufgabenmanager._2_application.TaskService;
import de.swe.aufgabenmanager._3_domain.entities.Task;

import java.util.List;
import java.util.Scanner;

import static java.lang.Thread.sleep;

public class CliEditTasks {

    private List<Task> tasks;
    private Scanner in;
    private CliTaskUtils cliTaskUtils;
    private TaskService taskService;

    public CliEditTasks(List<Task> tasks, TaskService taskService) {
        this.tasks = tasks;
        this.taskService = taskService;
        this.in = new Scanner(System.in);
        this.cliTaskUtils = new CliTaskUtils();
    }

    public void editTasks() throws InterruptedException {
        showTasks();
        System.out.println();
        System.out.println("0 - Zurück");
        System.out.println("Welche Aufgabe möchten Sie bearbeiten?");
        int taskNumber = CliUtils.readInt() - 1;
        if (taskNumber == -1) {
            return;
        }
        editTask(taskNumber);
    }

    public void completeTask() throws InterruptedException {
        showTasks();
        System.out.println();
        System.out.println("0 - Zurück");
        System.out.println("Welche Aufgabe möchten Sie abschließen?");
        int taskNumber = CliUtils.readInt() - 1;
        if (taskNumber == -1) {
            return;
        }
        Task task = tasks.get(taskNumber);
        task.setCompleted(true);
        taskService.saveTask(task);
        tasks = taskService.getTasksForUser(task.getUserId());
        System.out.println("Aufgabe abgeschlossen.");
        sleep(1000);
    }

    public void showTasks() {
        int i = 1;
        for (Task task : tasks) {
            System.out.println(i + " - " + task.getTitle() + " - " + task.getTaskPriority() + " - " + task.getDueDate());
            i++;
        }
    }

    private void editTask(int taskNumber) throws InterruptedException {
        Task task = tasks.get(taskNumber);
        System.out.println("Was möchten Sie bearbeiten?");
        System.out.println("1 - Titel");
        System.out.println("2 - Beschreibung");
        System.out.println("3 - Fälligkeitsdatum");
        System.out.println("4 - Priorität");
        System.out.println("5 - Zurück");
        int a = CliUtils.readInt();
        switch (a) {
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
                task.setDueDate(cliTaskUtils.enterDate());
                System.out.println("Fälligkeitsdatum geändert.");
                break;
            case 4:
                System.out.println("Geben Sie die neue Priorität ein:");
                task.setTaskPriority(cliTaskUtils.enterTaskPriority());
                System.out.println("Priorität geändert.");
                break;
            case 5:
                break;
            default:
                System.out.println("Fehler: Bitte geben Sie eine gültige Nummer ein.");
                editTask(taskNumber);
        }
        sleep(1000);
        taskService.saveTask(task);
        tasks = taskService.getTasksForUser(task.getUserId());
        editTasks();
    }
}
