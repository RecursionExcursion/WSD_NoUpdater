package com.example.wsd.deployables;


import com.example.wsd.deployables.deployable.Deployable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class StartUp implements Serializable {

    private String name;
    private final List<Deployable> deployables;
//    private final Set<Deployable> deployables;

    public StartUp() {
        deployables = new ArrayList<>();
//        deployables = new HashSet<>();
    }

    public String getName() {
        return name;
    }

    public List<Deployable> getDeployablePaths() {
        return deployables;
    }

//    public Set<Deployable> getDeployablePaths() {
//        return deployables;
//    }

    public void setName(String name) {
        this.name = name;
    }
}
