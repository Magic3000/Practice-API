package ru.magic3000.practice2.helpers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class JsonHelper {
    public static String readFromFile(String filePath) {
        try {
            return new String(Files.readAllBytes(Paths.get(filePath)));
        }
        catch (IOException exc) {
            exc.printStackTrace();
            return null;
        }
    }
}
