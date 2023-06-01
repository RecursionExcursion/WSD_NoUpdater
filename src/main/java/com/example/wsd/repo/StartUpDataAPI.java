package com.example.wsd.repo;

import com.example.wsd.deployables.StartUp;
import com.example.wsd.repo.serialization.SerializableStartUpList;
import com.example.wsd.repo.serialization.SerializationManager;

import java.util.List;

public class StartUpDataAPI {

    private static final SerializationManager SERIALIZATION_MANAGER = SerializationManager.INSTANCE;
    private static final List<StartUp> START_UP_LIST = SERIALIZATION_MANAGER.loadObject().getList();

    public List<StartUp> getInMemoryStartUps() {
        return START_UP_LIST;
    }

    public void saveStartUpsToMemory() {
        SerializableStartUpList ss = new SerializableStartUpList();
        ss.setList(START_UP_LIST);
        SERIALIZATION_MANAGER.saveObject(ss);
    }
}
