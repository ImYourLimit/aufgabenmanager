package de.swe.aufgabenmanager._0_plugin.cli.commands;

import de.swe.aufgabenmanager._0_plugin.cli.utils.CliTaskUtils;
import de.swe.aufgabenmanager._2_application.TaskService;
import de.swe.aufgabenmanager._3_domain.entities.Task;

public class EditTaskCommandContext {
    private Task task;
    private CliTaskUtils cliTaskUtils;
    private TaskService taskService;

    public EditTaskCommandContext(Task task, CliTaskUtils cliTaskUtils, TaskService taskService) {
        this.task = task;
        this.cliTaskUtils = cliTaskUtils;
        this.taskService = taskService;
    }

    public Task getTask() {
        return task;
    }

    public CliTaskUtils getCliTaskUtils() {
        return cliTaskUtils;
    }

    public TaskService getTaskService() {
        return taskService;
    }
}
