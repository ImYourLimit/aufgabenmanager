package de.swe.aufgabenmanager._0_plugin.cli.utils;

import de.swe.aufgabenmanager._0_plugin.repositories.GroupRepositoryImpl;
import de.swe.aufgabenmanager._0_plugin.repositories.UserRepositoryImpl;
import de.swe.aufgabenmanager._2_application.GroupService;
import de.swe.aufgabenmanager._3_domain.entities.Group;
import de.swe.aufgabenmanager._3_domain.interfaces.IGroupRepository;
import de.swe.aufgabenmanager._3_domain.interfaces.IUserRepository;

import java.util.List;
import java.util.Scanner;

public class CliGroupUtils {

    private Scanner in;
    private GroupService groupService;

    public CliGroupUtils(GroupService groupService) {
        this.in = new Scanner(System.in);

        this.groupService = groupService;
    }

    public void showGroups(List<Group> groups) {
        System.out.println("Gruppen:");
        for (Group g : groups) {
            System.out.println(g.getId() + " - " + g.getName());
        }
    }

    public Long enterGroupId() {
        Long groupId = in.nextLong();
        in.nextLine();
        while (!groupService.isIdValid(groupId)) {
            System.out.println("Fehler: Gruppe nicht gefunden. Bitte geben Sie eine gültige Gruppen-ID ein.");
            groupId = in.nextLong();
            in.nextLine();
        }
        return groupId;
    }
}
