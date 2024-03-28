package de.swe.aufgabenmanager._0_plugin.cli;

import de.swe.aufgabenmanager._0_plugin.repositories.CSVUserRepository;
import de.swe.aufgabenmanager._2_application.LoginService;
import de.swe.aufgabenmanager._2_application.RegisterService;
import de.swe.aufgabenmanager._3_domain.entities.UserRepository;

import java.util.Scanner;

import static java.lang.Thread.sleep;

public class CliStart {

    CliUtils cliUtils = new CliUtils();
    public void start() throws InterruptedException {
        System.out.println("Guten Tag! Wilkommen im Aufgabenmanager. Was möchten Sie tun?");
        System.out.println("1 - Login");
        System.out.println("2 - Registrieren");
        System.out.println("3 - Beenden");
        System.out.println("Bitte geben Sie die Nummer der gewünschten Option ein:");

        Scanner in = new Scanner(System.in);

        try {
            int a = in.nextInt();
            switch (a) {
                case 1:
                    cliUtils.clearConsole();
                    login();
                    break;
                case 2:
                    cliUtils.clearConsole();
                    registrieren();
                    break;
                case 3:
                    System.out.println("Auf Wiedersehen!");
                    sleep(1000);
                    System.exit(0);
                    break;
                default:
                    System.out.println("Fehler: Bitte geben Sie eine gültige Nummer ein.");
                    sleep(1000);
                    cliUtils.clearConsole();
                    start();
            }
        } catch(Exception e){
            in.nextLine();
            System.out.println("Fehler: Bitte geben Sie eine Nummer ein.");
            sleep(1000);
            cliUtils.clearConsole();
            start();
        }
    }

    private void registrieren() {
        System.out.println("Sie können jetzt einen neuen Benutzer registrieren.");
        System.out.println("Bitte geben Sie Ihren Benutzernamen ein:");
        Scanner in = new Scanner(System.in);
        String username = in.nextLine();

        UserRepository userRepository = new CSVUserRepository();

        RegisterService registerService = new RegisterService(userRepository);
        while(registerService.usernameTaken(username)) {
            System.out.println("Benutzername bereits vergeben. Bitte geben Sie einen anderen Benutzernamen ein:");
            username = in.nextLine();
        }
        System.out.println("Bitte geben Sie Ihr Passwort ein:");
        String password = in.nextLine();

        registerService.register(username, password);
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

        UserRepository userRepository = new CSVUserRepository();
        LoginService loginService = new LoginService(userRepository);

        while (!loginService.login(username, password)) {
            System.out.println("Fehler: Benutzername oder Passwort falsch.");
            System.out.println("Bitte versuchen Sie es erneut.");
            System.out.println("Bitte geben Sie Ihren Benutzernamen ein:");
            username = in.nextLine();
            System.out.println("Bitte geben Sie Ihr Passwort ein:");
            password = in.nextLine();
        }
        System.out.println("Login erfolgreich.");
    }
}
