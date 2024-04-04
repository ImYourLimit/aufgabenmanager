package de.swe.aufgabenmanager._0_plugin.cli.utils;

import de.swe.aufgabenmanager._3_domain.entities.Group;
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

    public LocalDateTime enterDate() {
        System.out.println("Geben sie das Jahr der Fälligkeit der Aufgabe ein:");
        int year = in.nextInt();
        System.out.println("Geben sie den Monat der Fälligkeit der Aufgabe ein:");
        int month = in.nextInt();
        System.out.println("Geben sie den Tag der Fälligkeit der Aufgabe ein:");
        int day = in.nextInt();
        System.out.println("Geben sie die Stunde der Fälligkeit der Aufgabe ein:");
        int hour = in.nextInt();
        System.out.println("Geben sie die Minute der Fälligkeit der Aufgabe ein:");
        int minute = in.nextInt();
        in.nextLine();
        try {
            LocalDateTime dueDate = LocalDateTime.of(year, month, day, hour, minute);
            return dueDate;
        } catch (Exception e) {
            System.out.println("Fehler: Bitte geben Sie ein gültiges Datum ein.");
            enterDate();
        }
        return null;
    }

    public TaskPriority enterTaskPriority() {
        System.out.println("Geben sie die Nummer der Priorität der Aufgabe ein (1 = LOW, 2 = MEDIUM, 3 = HIGH):");
        int priority = in.nextInt();
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
        int isGroupTaskInt = in.nextInt();
        in.nextLine();
        return isGroupTaskInt == 1;
    }

    public Long enterGroupId(List<Group> groups) {
        Long groupId = in.nextLong();
        in.nextLine();
        //ToDo: check if groupId is valid
        return groupId;
    }

    public void showTasks(List<Task> tasks) {
        int i = 1;
        for (Task task : tasks) {
            System.out.println(i + " - " + task.getTitle() + " - " + task.getTaskPriority() + " - " + task.getDueDate());
            i++;
        }
    }

}
