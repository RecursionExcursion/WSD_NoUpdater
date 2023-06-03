package com.example.wsd.repo.serialization;

public enum SerializationManager {

    INSTANCE;

    private final String pathToFileFolder = String.valueOf(DirectoryManager.getDir());
    private final String fileName = "startups";
    private final ObjectSerializer<SerializableStartUpList> objectSerializer =
            new ObjectSerializer<>(String.format("%s/%s", pathToFileFolder, fileName));

    public SerializableStartUpList loadObject() {
        try {
            return objectSerializer.load();
        } catch (Exception e) {
            objectSerializer.save(new SerializableStartUpList());
            return objectSerializer.load();
        }
    }

    public void saveObject(SerializableStartUpList obj) {
        objectSerializer.save(obj);
    }
}
