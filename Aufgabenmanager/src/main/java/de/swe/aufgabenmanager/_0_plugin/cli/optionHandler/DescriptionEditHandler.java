package de.swe.aufgabenmanager._0_plugin.cli.optionHandler;

import de.swe.aufgabenmanager._0_plugin.cli.commands.EditTaskCommandContext;
import de.swe.aufgabenmanager._0_plugin.cli.commands.UpdateDescriptionCommand;
public class DescriptionEditHandler extends EditTaskOptionHandler{
    @Override
    public void handleRequest(int option, EditTaskCommandContext context) {
        if (option == 2) {
            new UpdateDescriptionCommand(context.getTask(), context).execute();
        } else if (successor != null) {
            successor.handleRequest(option, context);
        }
    }
}
