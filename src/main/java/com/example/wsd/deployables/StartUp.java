package com.example.wsd.deployables;


import com.example.wsd.deployables.deploy.Deployable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class StartUp implements Serializable {

    private String name;
    private final List<Deployable> deployables;

    public StartUp(String name) {
        this.name = name;
        deployables = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public List<Deployable> getDeployablePaths() {
        return deployables;
    }

    public void setName(String name) {
        this.name = name;
    }
}
