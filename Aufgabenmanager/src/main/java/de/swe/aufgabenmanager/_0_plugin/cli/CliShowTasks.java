package de.swe.aufgabenmanager._0_plugin.cli;

import de.swe.aufgabenmanager._2_application.TaskService;
import de.swe.aufgabenmanager._3_domain.entities.Task;

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
    }

}
