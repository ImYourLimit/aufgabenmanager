package de.swe.aufgabenmanager._0_plugin.cli.commands;

import de.swe.aufgabenmanager._3_domain.entities.Task;

public class DeleteTaskCommand implements TaskCommand {
    private Task task;
    private EditTaskCommandContext context;

    public DeleteTaskCommand(Task task, EditTaskCommandContext context) {
        this.task = task;
        this.context = context;
    }

    @Override
    public void execute() {
        context.getTaskService().deleteTask(task);
        System.out.println("Aufgabe gelöscht.");
    }
}
