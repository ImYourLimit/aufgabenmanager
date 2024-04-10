package de.swe.aufgabenmanager._0_plugin.cli;

import de.swe.aufgabenmanager._0_plugin.cli.utils.CliUtils;
import de.swe.aufgabenmanager._0_plugin.repositories.UserRepositoryImpl;
import de.swe.aufgabenmanager._2_application.UserService;
import de.swe.aufgabenmanager._3_domain.interfaces.IUserRepository;

import java.util.Scanner;

public class CliStart {

    IUserRepository userRepository;
    UserService userService;

    public CliStart() {
        this.userRepository = new UserRepositoryImpl();
        this.userService = new UserService(userRepository);
    }

    public void start() {
        System.out.println("Guten Tag! Wilkommen im Aufgabenmanager. Was möchten Sie tun?");
        System.out.println("1 - Login");
        System.out.println("2 - Registrieren");
        System.out.println();
        System.out.println("0 - Beenden");
        System.out.println("Bitte geben Sie die Nummer der gewünschten Option ein:");

        Scanner in = new Scanner(System.in);

        try {
            int a = in.nextInt();
            switch (a) {
                case 0:
                    System.out.println("Auf Wiedersehen!");
                    CliUtils.sleepFor(1000);
                    System.exit(0);
                    break;
                case 1:
                    CliUtils.clearConsole();
                    login();
                    break;
                case 2:
                    CliUtils.clearConsole();
                    registrieren();
                    break;
                default:
                    System.out.println("Fehler: Bitte geben Sie eine gültige Nummer ein.");
                    CliUtils.sleepFor(1000);
                    CliUtils.clearConsole();
                    start();
            }
        } catch(Exception e){
            in.nextLine();
            System.out.println("Fehler: Bitte geben Sie eine Nummer ein.");
            CliUtils.sleepFor(1000);
            CliUtils.clearConsole();
            start();
        }
    }

    private void registrieren() {
        System.out.println("Sie können jetzt einen neuen Benutzer registrieren.");
        System.out.println("Bitte geben Sie Ihren Benutzernamen ein:");
        Scanner in = new Scanner(System.in);
        String username = in.nextLine();

        while(userService.usernameTaken(username)) {
            System.out.println("Benutzername bereits vergeben. Bitte geben Sie einen anderen Benutzernamen ein:");
            username = in.nextLine();
        }
        System.out.println("Bitte geben Sie Ihr Passwort ein:");
        String password = in.nextLine();

        userService.register(username, password);
        System.out.println("Benutzer erfolgreich registriert.");
        login();
    }

    private void login() {
        System.out.println("Login");
        System.out.println("Bitte geben Sie Ihren Benutzernamen ein:");
        Scanner in = new Scanner(System.in);
        String username = in.nextLine();
        System.out.println("Bitte geben Sie Ihr Passwort ein:");
        String password = in.nextLine();

        while (!userService.login(username, password)) {
            System.out.println("Fehler: Benutzername oder Passwort falsch.");
            System.out.println("Bitte versuchen Sie es erneut.");
            System.out.println("Bitte geben Sie Ihren Benutzernamen ein:");
            username = in.nextLine();
            System.out.println("Bitte geben Sie Ihr Passwort ein:");
            password = in.nextLine();
        }
        System.out.println("Login erfolgreich.");
        CliUtils.clearConsole();
        CliMenu menu = new CliMenu(userService.getUserId(username));
        menu.start();
    }
}
