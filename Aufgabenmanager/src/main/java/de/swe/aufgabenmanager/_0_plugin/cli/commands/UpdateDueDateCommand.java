package de.swe.aufgabenmanager._0_plugin.cli.commands;

import de.swe.aufgabenmanager._3_domain.entities.Task;

public class UpdateDueDateCommand implements TaskCommand{
    private Task task;
    private EditTaskCommandContext context;

    public UpdateDueDateCommand(Task task, EditTaskCommandContext context) {
        this.task = task;
        this.context = context;
    }

    @Override
    public void execute() {
        System.out.println("Geben Sie das neue Fälligkeitsdatum ein:");
        task.setDueDate(context.getCliTaskUtils().enterDueDate());
        System.out.println("Fälligkeitsdatum geändert.");
    }
}
