package de.swe.aufgabenmanager._0_plugin.cli;

import de.swe.aufgabenmanager._2_application.TaskService;
import de.swe.aufgabenmanager._3_domain.entities.Task;
import de.swe.aufgabenmanager._3_domain.vo.TaskPriority;

import java.util.List;
import java.util.Scanner;

public class CliShowTasks {
    private List<Task> tasks;
    private Scanner in;
    private CliTaskUtils cliTaskUtils;
    private TaskService taskService;

    public CliShowTasks(List<Task> tasks, TaskService taskService) {
        this.tasks = tasks;
        this.taskService = taskService;
        this.in = new Scanner(System.in);
        this.cliTaskUtils = new CliTaskUtils();
    }

    public void start() {
        cliTaskUtils.showTasks(tasks);
        System.out.println();
        System.out.println("Welche Aufgabe möchten sie anzeigen lassen?");
        System.out.println("0 - Zurück");

        int taskNumber = CliUtils.readInt() - 1;
        if (taskNumber == -1) {
            return;
        }
        showTask(taskNumber);
    }

    private void showTask(int taskNumber) {
        Task task = tasks.get(taskNumber);
        System.out.println("Titel: " + task.getTitle());
        System.out.println("Beschreibung: " + task.getDescription());
        System.out.println("Fälligkeitsdatum: " + task.getDueDate());
        System.out.println("Priorität: " + task.getTaskPriority());

        System.out.println();
        System.out.println("0 - Zurück");

        int a = CliUtils.readInt();
        switch (a) {
            case 0:
                return;
            default:
                System.out.println("Fehler: Bitte geben Sie eine gültige Nummer ein.");
                showTask(taskNumber);
        }


    }

}
