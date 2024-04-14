package de.swe.aufgabenmanager._0_plugin.cli;

import de.swe.aufgabenmanager._0_plugin.cli.commands.EditTaskCommandContext;
import de.swe.aufgabenmanager._0_plugin.cli.optionHandler.*;
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
        EditTaskCommandContext context = new EditTaskCommandContext(task, cliTaskUtils, taskService);

        System.out.println("Was möchten Sie bearbeiten?");
        System.out.println("1 - Titel");
        System.out.println("2 - Beschreibung");
        System.out.println("3 - Fälligkeitsdatum");
        System.out.println("4 - Priorität");
        System.out.println("5 - Löschen");
        System.out.println();
        System.out.println("0 - Zurück");

        int option = CliUtils.readInt();
        EditTaskOptionHandler chain = buildHandlerChain();
        chain.handleRequest(option, context);

        if (option > 5) {
            System.out.println("Fehler: Bitte geben Sie eine gültige Nummer ein.");
            editTask(taskNumber);
        } else if (option != 0) {
            CliUtils.sleepFor(1000);
            taskService.saveTask(task);
            tasks = taskService.getTasksForUser(task.getUserId());
            editTasksMenu();
        }
    }

    private EditTaskOptionHandler buildHandlerChain() {
        EditTaskOptionHandler titleHandler = new TitleEditHandler();
        EditTaskOptionHandler descriptionHandler = new DescriptionEditHandler();
        EditTaskOptionHandler dueDateHandler = new DueDateEditHandler();
        EditTaskOptionHandler priorityHandler = new PriorityEditHandler();
        EditTaskOptionHandler deleteHandler = new TaskDeleteHandler();

        titleHandler.setSuccessor(descriptionHandler);
        descriptionHandler.setSuccessor(dueDateHandler);
        dueDateHandler.setSuccessor(priorityHandler);
        priorityHandler.setSuccessor(deleteHandler);

        return titleHandler;
    }
}
