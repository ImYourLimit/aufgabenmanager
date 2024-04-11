package de.swe.aufgabenmanager._0_plugin.cli.utils;

import de.swe.aufgabenmanager._3_domain.entities.Task;
import de.swe.aufgabenmanager._3_domain.vo.TaskPriority;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

public class CliTaskUtils {

    private Scanner in;

    public CliTaskUtils() {
        this.in = new Scanner(System.in);
    }

    public String enterTitle() {
        System.out.println("Geben sie den Titel der Aufgabe ein:");
        String title = in.nextLine();
        return title;
    }

    public String enterDescription() {
        System.out.println("Geben sie die Beschreibung der Aufgabe ein:");
        String description = in.nextLine();
        return description;
    }

    public LocalDateTime enterDueDate() {
        System.out.println("Geben sie das Jahr der Fälligkeit der Aufgabe ein:");
        int year = CliUtils.readInt();
        System.out.println("Geben sie den Monat der Fälligkeit der Aufgabe ein:");
        int month = CliUtils.readInt();
        System.out.println("Geben sie den Tag der Fälligkeit der Aufgabe ein:");
        int day = CliUtils.readInt();
        System.out.println("Geben sie die Stunde der Fälligkeit der Aufgabe ein:");
        int hour = CliUtils.readInt();
        System.out.println("Geben sie die Minute der Fälligkeit der Aufgabe ein:");
        int minute = CliUtils.readInt();
        in.nextLine();
        try {
            LocalDateTime dueDate = LocalDateTime.of(year, month, day, hour, minute);
            return dueDate;
        } catch (Exception e) {
            System.out.println("Fehler: Bitte geben Sie ein gültiges Datum ein.");
            enterDueDate();
        }
        return null;
    }

    public TaskPriority enterTaskPriority() {
        System.out.println("Geben sie die Nummer der Priorität der Aufgabe ein (1 = LOW, 2 = MEDIUM, 3 = HIGH):");
        int priority = CliUtils.readInt();
        switch (priority) {
            case 1:
                return TaskPriority.LOW;
            case 2:
                return TaskPriority.MEDIUM;
            case 3:
                return TaskPriority.HIGH;
            default:
                System.out.println("Fehler: Bitte geben Sie eine gültige Nummer ein.");
                enterTaskPriority();
        }
        return null;
    }

    public boolean enterIsGroupTask() {
        System.out.println("Ist die Aufgabe eine Gruppenaufgabe? (1 = Ja, 2 = Nein)");
        int isGroupTaskInt = CliUtils.readInt();
        in.nextLine();
        return isGroupTaskInt == 1;
    }

    public void showTasks(List<Task> tasks) {
        int i = 1;
        for (Task task : tasks) {
            System.out.println(i + " - " + task.getTitle() + " - " + task.getTaskPriority() + " - " + task.getDueDate());
            i++;
        }
    }

}
