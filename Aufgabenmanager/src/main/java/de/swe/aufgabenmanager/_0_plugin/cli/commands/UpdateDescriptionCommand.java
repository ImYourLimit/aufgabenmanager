package de.swe.aufgabenmanager._0_plugin.cli.commands;

import de.swe.aufgabenmanager._3_domain.entities.Task;

public class UpdateDescriptionCommand implements TaskCommand{
    private Task task;
    private EditTaskCommandContext context;

    public UpdateDescriptionCommand(Task task, EditTaskCommandContext context) {
        this.task = task;
        this.context = context;
    }

    @Override
    public void execute() {
        System.out.println("Geben Sie die neue Beschreibung ein:");
        String newDescription = context.getCliTaskUtils().enterDescription();
        task.setDescription(newDescription);
        System.out.println("Beschreibung geändert.");
    }
}
