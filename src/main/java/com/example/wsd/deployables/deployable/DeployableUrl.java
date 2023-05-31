package com.example.wsd.deployables.deployable;

import java.net.MalformedURLException;
import java.net.URL;

public class DeployableUrl extends Deployable {

    private final URL url;

    public DeployableUrl(String Path) throws MalformedURLException {
        this.url = new URL(Path);
    }

    public URL getURL(){
        return url;
    }
}
