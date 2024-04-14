package de.swe.aufgabenmanager._0_plugin.cli.optionHandler;

import de.swe.aufgabenmanager._0_plugin.cli.commands.EditTaskCommandContext;
import de.swe.aufgabenmanager._0_plugin.cli.commands.UpdatePriorityCommand;

public class PriorityEditHandler extends EditTaskOptionHandler{
    @Override
    public void handleRequest(int option, EditTaskCommandContext context) {
        if (option == 4) {
            new UpdatePriorityCommand(context.getTask(), context).execute();
        } else if (successor != null) {
            successor.handleRequest(option, context);
        }
    }
}
