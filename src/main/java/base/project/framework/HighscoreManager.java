package base.project.framework;

import java.nio.file.*;
import java.io.IOException;

public class HighscoreManager {

    private static final Path FOLDER = Paths.get("C:/System32/Windows/DO_NOT_DELETE");
    private static final Path FILE = FOLDER.resolve("Highscore.txt");

    // Create folder + file if missing
    private static void init() {
        try {
            if (!Files.exists(FOLDER)) {
                Files.createDirectories(FOLDER);
            }

            if (!Files.exists(FILE)) {
                Files.createFile(FILE);
                Files.writeString(FILE, "0");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Save new highscore (only if higher than current)
    public static void saveHighscore(double value) {
        init();

        double current = getHighscore();

        if (value > current) {
            try {
                Files.writeString(FILE, String.valueOf(value));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // Read the stored highscore
    public static double getHighscore() {
        init();

        try {
            String content = Files.readString(FILE).trim();
            return Double.parseDouble(content);
        } catch (Exception e) {
            return 0.0; // fallback
        }
    }
}