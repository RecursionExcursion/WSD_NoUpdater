package com.example.wsd.repo.serialization;

public enum SerializationManager {

    INSTANCE;

    private final String pathToFileFolder = "src/main/resources/";
    private final String fileName = "startups";
    private final ObjectSerializer<SerializableStartUpList> objectSerializer =
            new ObjectSerializer<>(pathToFileFolder + fileName);

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
