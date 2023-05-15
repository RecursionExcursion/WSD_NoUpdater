package com.example.wsd.deployables;


import com.example.wsd.deployables.deploy.Deployable;
import com.example.wsd.deployables.deploy.DeployableFile;
import com.example.wsd.deployables.deploy.DeployableUrl;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

public class Deployer {

    private static final Desktop DESKTOP = Desktop.getDesktop();

    public static void deploy(Deployable deployable) {
        try {
            if (deployable instanceof DeployableUrl) {
                deployURL(((DeployableUrl) deployable).getURL());

            } else if (deployable instanceof DeployableFile) {
                deployFile(((DeployableFile) deployable).getFile());
            }
        } catch (URISyntaxException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void deployURL(URL url) throws URISyntaxException, IOException {
        DESKTOP.browse(url.toURI());
    }

    private static void deployFile(File file) throws IOException {
        DESKTOP.open(file);
    }
}
