package com.example.wsd.fx_util;


import java.net.URL;

public enum CssManager {

    INSTANCE;

    private final String darkCssPath = "/css/dark.css";

    public String getCssUrl() {
        URL resource = getClass().getResource(darkCssPath);
        return returnStringOrExit(resource);
    }

    private static String returnStringOrExit(URL cssUrl) {
        if (cssUrl == null) {
            System.out.println("Resource not found. Aborting application.");
            System.exit(-1);
        }
        return cssUrl.toExternalForm();
    }
}
