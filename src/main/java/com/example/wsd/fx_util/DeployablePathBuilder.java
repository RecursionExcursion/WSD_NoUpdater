package com.example.wsd.fx_util;

import com.example.wsd.deployables.deployable.Deployable;
import com.example.wsd.deployables.deployable.DeployableFile;
import com.example.wsd.deployables.deployable.DeployableUrl;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

public class DeployablePathBuilder {

    public static boolean testPath(String path) {
        if (path.isEmpty()) return false;
        try {
            //Test if path is valid File Path
            if (new File(path).canExecute()) return true;
            //Check if Path is Valid URL
            new URL(path).toURI();
            return true;
        } catch (MalformedURLException | URISyntaxException e) {
            return false;
        }
    }

    public static Deployable buildDeployable(String s) {
        try {
            if (new File(s).canExecute()) {
                return new DeployableFile(s);
            } else {
                return new DeployableUrl(s);
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }
}
