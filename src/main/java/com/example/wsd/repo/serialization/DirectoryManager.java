package com.example.wsd.repo.serialization;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

final class DirectoryManager {

    //TODO bind to Serialization manager for proper pathing during deployment

    private static final String USER_HOME = System.getProperty("user.home");

    private static final String COMPANY = "foofinc";
    private static final String FOLDER = "WSD_AppData";

    private static final String FOLDER_PATH = String.format("Documents/%s/%s", COMPANY, FOLDER);

    private static final Path applicationDir = Paths.get(USER_HOME, FOLDER_PATH);


    static Path getDir() {
        if (!Files.exists(applicationDir)) {
            createDir();
        }
        return applicationDir;
    }

    private static void createDir() {
        try {
            Files.createDirectories(applicationDir);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
