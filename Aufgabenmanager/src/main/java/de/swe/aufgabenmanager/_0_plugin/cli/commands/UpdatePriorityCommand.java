package de.swe.aufgabenmanager._0_plugin.cli.commands;

import de.swe.aufgabenmanager._3_domain.entities.Task;

public class UpdatePriorityCommand implements TaskCommand{
    private Task task;
    private EditTaskCommandContext context;

    public UpdatePriorityCommand(Task task, EditTaskCommandContext context) {
        this.task = task;
        this.context = context;
    }

    @Override
    public void execute() {
        System.out.println("Geben Sie die neue Priorität ein:");
        task.setTaskPriority(context.getCliTaskUtils().enterTaskPriority());
        System.out.println("Priorität geändert.");
    }
}
