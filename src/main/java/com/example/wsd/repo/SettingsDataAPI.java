package com.example.wsd.repo;

import com.example.wsd.repo.serialization.SerializationManager;
import com.example.wsd.repo.serialization.GlobalSettings;

public enum SettingsDataAPI {

    INSTANCE;

    private static final SerializationManager SERIALIZATION_MANAGER = SerializationManager.INSTANCE;
    private static GlobalSettings GLOBAL_SETTINGS = SERIALIZATION_MANAGER.loadSettingsData();

    //TODO Re-check logic, Possibly create tasks to run in background for more fluid application

    public GlobalSettings read() {
        return GLOBAL_SETTINGS = SERIALIZATION_MANAGER.loadSettingsData();
    }

    public void update() {
        SERIALIZATION_MANAGER.saveSettingsData(GLOBAL_SETTINGS);
    }

    public void reset(){
        GLOBAL_SETTINGS = new GlobalSettings();
        update();
    }
}
