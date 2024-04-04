package de.swe.aufgabenmanager._0_plugin.cli;

import de.swe.aufgabenmanager._3_domain.entities.Group;

import java.util.List;
import java.util.Scanner;

public class CliGroupUtils {

    private Scanner in;

    public CliGroupUtils() {
        this.in = new Scanner(System.in);
    }

    public void showGroups(List<Group> groups) {
        System.out.println("Gruppen:");
        for (Group g : groups) {
            System.out.println(g.getId() + " - " + g.getName());
        }
    }
}
