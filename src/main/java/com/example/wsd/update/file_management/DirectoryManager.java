package com.example.wsd.update.file_management;

import com.example.wsd.properties.PropertyManager;

import java.io.File;
import java.io.FileNotFoundException;

public class DirectoryManager {


    /**
     * @return Directory that the .exe exists in
     */
    public static File getProjectDirectory() {
        return new File(System.getProperty("user.dir"));
    }

    /**
     * @return Directory inside the Directory that the .exe exists in
     * i.e.- auto_updater/temp
     */
    public static File getTempDirectory() {
        String tempDirectoryName = PropertyManager.INSTANCE.getProperties().getTempDirectoryName();
        return new File(String.format("%s/%s", getProjectDirectory(), tempDirectoryName));
    }

    /**
     * @return Parent Directory that the auto_updater project exists in
     */
    public static File getUpdaterDirectory() {

        String updaterFolderName = PropertyManager.INSTANCE.getProperties().getUpdaterFolderName();

        File projectDirectory = getProjectDirectory();
        File[] files = projectDirectory.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.toPath().endsWith(updaterFolderName)) {
                    return file;
                }
            }
        }
        try {
            throw new FileNotFoundException("Updater file not found");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
