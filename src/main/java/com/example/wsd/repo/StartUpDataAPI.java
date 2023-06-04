package com.example.wsd.repo;

import com.example.wsd.deployables.StartUp;
import com.example.wsd.repo.serialization.SerializableStartUpList;
import com.example.wsd.repo.serialization.SerializationManager;

import java.util.List;

public enum StartUpDataAPI {

    INSTANCE;

    private static final SerializationManager SERIALIZATION_MANAGER = SerializationManager.INSTANCE;
    private static final List<StartUp> GLOBAL_START_UP_LIST = SERIALIZATION_MANAGER.loadStartUpData().getList();

    public void create(StartUp newStartUp) {
        GLOBAL_START_UP_LIST.add(newStartUp);
        saveGlobalList();
    }

    public List<StartUp> read() {
        return GLOBAL_START_UP_LIST;
    }

    public void update() {
        saveGlobalList();
    }

    public void delete(StartUp startUpToBeDeleted) {
        GLOBAL_START_UP_LIST.remove(startUpToBeDeleted);
        saveGlobalList();
    }

    private void saveGlobalList() {
        SERIALIZATION_MANAGER.saveStartUpData(mapToSerializableContainer());
    }

    private SerializableStartUpList mapToSerializableContainer() {
        SerializableStartUpList ss = new SerializableStartUpList();
        ss.setList(GLOBAL_START_UP_LIST);
        return ss;
    }
}
