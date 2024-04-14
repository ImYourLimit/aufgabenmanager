package de.swe.aufgabenmanager._0_plugin.cli.optionHandler;

import de.swe.aufgabenmanager._0_plugin.cli.commands.DeleteTaskCommand;
import de.swe.aufgabenmanager._0_plugin.cli.commands.EditTaskCommandContext;

public class TaskDeleteHandler extends EditTaskOptionHandler{
    @Override
    public void handleRequest(int option, EditTaskCommandContext context) {
        if (option == 5) {
            new DeleteTaskCommand(context.getTask(), context).execute();
        } else if (successor != null) {
            successor.handleRequest(option, context);
        }
    }
}
