package com.example.wsd.repo.serialization;


import com.example.wsd.deployables.StartUp;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SerializableStartUpList implements Serializable {

    private List<StartUp> list = new ArrayList<>();

    public List<StartUp> getList() {
        return list;
    }

    public void setList(List<StartUp> list) {
        this.list = list;
    }
}
