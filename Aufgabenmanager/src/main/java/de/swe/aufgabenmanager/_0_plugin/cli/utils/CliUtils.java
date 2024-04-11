package de.swe.aufgabenmanager._0_plugin.cli.utils;

import java.io.IOException;
import java.util.Scanner;

public class CliUtils {
    public static void clearConsole()
    {
        try {
            String operatingSystem = System.getProperty("os.name");

            if (operatingSystem.contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                System.out.print("\033[H\033[2J");
                System.out.flush();
            }
        } catch (IOException | InterruptedException e) {

        }
        for (int i = 0; i < 30; i++) {
            System.out.println();
        }
    }

    public static void sleepFor(int time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
