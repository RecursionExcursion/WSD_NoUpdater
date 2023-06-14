package com.example.wsd.update;

import com.example.wsd.properties.PropertyManager;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Optional;

public class UpdateManager {

    private final String updaterFolderName = PropertyManager.INSTANCE.getProperties().getUpdaterFolderName();
    private final String updaterExeName = PropertyManager.INSTANCE.getProperties().getUpdaterExeName();

    public void deployUpdater() throws IOException {
        File mainDirectory = getMainDirectory();

        Optional<File[]> optionalDirFiles = Optional.ofNullable(mainDirectory.listFiles());

        Optional<File[]> optionalUpdaterFiles = Optional.empty();

        for (File file : optionalDirFiles.orElseThrow()) {
            if (file.isDirectory() && file.toPath().endsWith(updaterFolderName)) {
                optionalUpdaterFiles = Optional.ofNullable(file.listFiles());
                break;
            }
        }

        for (File file : optionalUpdaterFiles.orElseThrow()) {
            if (file.toPath().endsWith(updaterExeName)) {
                Desktop.getDesktop().open(file);
                System.exit(0);
            }
        }
    }


    private static File getMainDirectory() {
        return new File(System.getProperty("user.dir"));
    }
}
