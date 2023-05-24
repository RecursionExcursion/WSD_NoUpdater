package com.example.wsd.fx_util;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

public class PathTester {

    public static boolean testPath(String path) {
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
}
