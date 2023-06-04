package com.example.wsd.repo;

import com.example.wsd.repo.serialization.SerializationManager;
import com.example.wsd.repo.serialization.GlobalSettings;

public enum SettingsDataAPI {

    INSTANCE;

    private static final SerializationManager SERIALIZATION_MANAGER = SerializationManager.INSTANCE;
    private static final GlobalSettings GLOBAL_SETTINGS = SERIALIZATION_MANAGER.loadSettingsData();

    public GlobalSettings read() {
        return GLOBAL_SETTINGS;
    }

    public void update(GlobalSettings settings) {
        SERIALIZATION_MANAGER.saveSettingsData(settings);

    }
}
