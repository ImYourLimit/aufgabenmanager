package de.swe.aufgabenmanager._0_plugin.cli.commands;

import de.swe.aufgabenmanager._3_domain.entities.Task;

public class UpdateTitleCommand implements TaskCommand {
    private Task task;
    private EditTaskCommandContext context;

    public UpdateTitleCommand(Task task, EditTaskCommandContext context) {
        this.task = task;
        this.context = context;
    }

    @Override
    public void execute() {
        System.out.println("Geben Sie den neuen Titel ein:");
        String newTitle = context.getCliTaskUtils().enterTitle();
        task.setTitle(newTitle);
        System.out.println("Titel geändert.");
    }
}
