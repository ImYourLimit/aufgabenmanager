package de.swe.aufgabenmanager._0_plugin.cli;

import de.swe.aufgabenmanager._2_application.GroupService;
import de.swe.aufgabenmanager._3_domain.entities.Group;
import de.swe.aufgabenmanager._3_domain.entities.IGroupRepository;
import de.swe.aufgabenmanager._3_domain.entities.IUserRepository;
import de.swe.aufgabenmanager._3_domain.entities.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static java.lang.Thread.sleep;

public class CliGroups {
    private GroupService groupService;

    public CliGroups(IGroupRepository groupRepository, IUserRepository userRepository) {
        this.groupService = new GroupService(groupRepository, userRepository);
    }
    public void start() throws InterruptedException {
        System.out.println("Gruppen bearbeiten");
        System.out.println("Was möchten Sie tun?");
        System.out.println("1 - Gruppen anzeigen");
        System.out.println("2 - Gruppe hinzufügen");
        System.out.println("3 - Gruppe löschen");
        System.out.println("4 - Gruppe bearbeiten");
        System.out.println("5 - Zurück");

        Scanner in = new Scanner(System.in);

        try {
            int a = CliUtils.readInt();
            switch (a) {
                case 1:
                    CliUtils.clearConsole();
                    showGroups();
                    System.out.println();
                    System.out.println("0 - Zurück");

                    while(true)  {
                        a = CliUtils.readInt();
                        if (a == 0) {
                            break;
                        } else {
                            System.out.println("Fehler: Bitte geben Sie eine gültige Nummer ein.");
                        }
                    }
                    break;
                case 2:
                    CliUtils.clearConsole();
                    addGroup();
                    break;
                case 3:
                    CliUtils.clearConsole();
                    deleteGroup();
                    break;
                case 4:
                    CliUtils.clearConsole();
                    editGroup();
                    break;
                case 5:
                    CliUtils.clearConsole();
                    return;
                default:
                    System.out.println("Fehler: Bitte geben Sie eine gültige Nummer ein.");
                    sleep(1000);
                    CliUtils.clearConsole();
                    start();
            }
        } catch(Exception e){
            in.nextLine();
            System.out.println("Fehler: Bitte geben Sie eine Nummer ein.");
            sleep(1000);
            CliUtils.clearConsole();
            start();
        }
        CliUtils.clearConsole();
        start();
    }

    public void addGroup() {
        System.out.println("Gruppe hinzufügen");
        System.out.println("Geben Sie den Namen der Gruppe ein:");
        Scanner in = new Scanner(System.in);
        String name = in.nextLine();
        System.out.println();
        List<Long> userIds = selectUsers();
        groupService.addGroup(name, userIds);
    }

    public void deleteGroup() throws InterruptedException {
        System.out.println("Gruppe löschen");
        showGroups();
        System.out.println();
        System.out.println("0 - Zurück");
        System.out.println("Wählen Sie eine Gruppe aus, die Sie löschen möchten:");
        int groupId = CliUtils.readInt();
        Group group = groupService.getGroupById((long) groupId);
        groupService.deleteGroup(group);
        System.out.println("Gruppe gelöscht");
        sleep(1000);
    }

    public void editGroup() {
        System.out.println("Gruppe bearbeiten");
        showGroups();
        System.out.println();
        System.out.println("0 - Zurück");
        System.out.println();
        System.out.println("Wählen Sie eine Gruppe aus, die Sie bearbeiten möchten:");
        Long groupId = selectGroup();

        Group group = groupService.getGroupById(groupId);
        System.out.println("Gruppe bearbeiten");
        System.out.println("Geben Sie den neuen Namen der Gruppe ein:");
        Scanner in = new Scanner(System.in);
        String name = in.nextLine();
        System.out.println();
        List<Long> userIds = selectUsers();
        group.setName(name);
        group.setUserIds(userIds);
        groupService.saveGroup(group);
    }

    private List<Long> selectUsers() {
        CliUtils.clearConsole();
        List<User> users = groupService.getAllUsers();
        List<Long> userIds = new ArrayList();
        boolean finished = false;
        while(!finished) {
            showUsers(users);
            System.out.println("0 - Fertig");
            System.out.println("Wählen Sie einen Benutzer aus, den Sie hinzufügen möchten:");
            Long userId = (long) CliUtils.readInt();
            if (userId == 0) {
                finished = true;
            } else if (userId > 0 && users.stream().anyMatch(u -> u.getId() == userId)) {
                userIds.add(userId);
                users.removeIf(u -> u.getId() == userId);
            } else {
                System.out.println("Fehler: Bitte geben Sie eine gültige Nummer ein.");
            }
        }
        return userIds;
    }

    private Long selectGroup() {
        int groupId = CliUtils.readInt();
        if (groupService.getAllGroups().isEmpty()) {
            System.out.println("Es gibt keine Gruppen.");
            return null;
        } else if (groupId > 0 && groupService.getAllGroups().stream().anyMatch(g -> g.getId() == groupId)) {
            return (long) groupId;
        } else {
            System.out.println("Fehler: Bitte geben Sie eine gültige Nummer ein.");
            return selectGroup();
        }
    }

    private void showUsers(List<User> users) {
        System.out.println("Benutzer:");
        for (User u : users) {
            System.out.println(u.getId() + " - " + u.getUsername());
        }
    }

    private void showGroups() {
        System.out.println("Gruppen:");
        List<Group> groups = groupService.getAllGroups();
        for (Group g : groups) {
            System.out.println(g.getId() + " - " + g.getName());
        }
    }
}
