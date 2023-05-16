package com.example.wsd.repo;

import com.example.wsd.deployables.StartUp;
import com.example.wsd.serialization.SerializableStartUps;
import com.example.wsd.serialization.SerializationManager;

import java.util.List;

public class StartUpDataAPI {

    private static final SerializationManager SERIALIZATION_MANAGER = SerializationManager.INSTANCE;

    public List<StartUp> getInMemoryStartUps() {
        return SERIALIZATION_MANAGER.loadObject().getList();
    }

    public void saveStartUpsToMemory(List<StartUp> startUps) {
        SerializableStartUps ss = new SerializableStartUps();
        ss.getList().addAll(startUps);
        SERIALIZATION_MANAGER.saveObject(ss);
    }
}
