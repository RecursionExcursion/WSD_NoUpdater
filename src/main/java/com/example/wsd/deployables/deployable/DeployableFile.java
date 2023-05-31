package com.example.wsd.deployables.deployable;

import java.io.File;

public class DeployableFile extends Deployable {

    private final File file;

    public DeployableFile(String path) {
        this.file = new File(path);
    }

    public File getFile() {
        return file;
    }
}
