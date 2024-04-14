package de.swe.aufgabenmanager._0_plugin.cli.optionHandler;

import de.swe.aufgabenmanager._0_plugin.cli.commands.EditTaskCommandContext;
import de.swe.aufgabenmanager._0_plugin.cli.utils.CliTaskUtils;
import de.swe.aufgabenmanager._3_domain.entities.Task;

public abstract class EditTaskOptionHandler {
    protected EditTaskOptionHandler successor;

    public void setSuccessor(EditTaskOptionHandler successor) {
        this.successor = successor;
    }

    public abstract void handleRequest(int option, EditTaskCommandContext context);
}
