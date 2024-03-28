package de.swe.aufgabenmanager._0_plugin.cli;

import java.io.IOException;

public class CliUtils {
    public final static void clearConsole()
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
}
