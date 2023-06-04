package com.example.wsd.repo.serialization;

public enum SerializationManager {

    INSTANCE;

    private final String pathToFileFolder = String.valueOf(DirectoryManager.getDir());

    private final String startUpsFileName = "startups";
    private final ObjectSerializer<SerializableStartUpList> startUpSerializer =
            new ObjectSerializer<>(String.format("%s/%s", pathToFileFolder, startUpsFileName));

    private final String settingsFileName = "settings";
    private final ObjectSerializer<GlobalSettings> settingsSerializer =
            new ObjectSerializer<>(String.format("%s/%s", pathToFileFolder, settingsFileName));

    public SerializableStartUpList loadStartUpData() {
        try {
            return startUpSerializer.load();
        } catch (Exception e) {
            startUpSerializer.save(new SerializableStartUpList());
            return startUpSerializer.load();
        }
    }

    public void saveStartUpData(SerializableStartUpList obj) {
        startUpSerializer.save(obj);
    }

    public GlobalSettings loadSettingsData() {
        try {
            return settingsSerializer.load();
        } catch (Exception e) {
            settingsSerializer.save(new GlobalSettings(750));
            return settingsSerializer.load();
        }
    }

    public void saveSettingsData(GlobalSettings settings) {
        settingsSerializer.save(settings);
    }
}
