package com.example.wsd.serialization;

/**
 * Manager of ObjectSerializer
 * 'SerializablePlaceHolder' type should be replaced by the Object to be Serialized that implements the Serializable interface
 * FilePath and FileName must be updated
 * All are marked with //TODO
 * <p>
 * Handling multiple ObjectSerializer's are possible
 */


public enum SerializationManager {

    INSTANCE;

    private final String pathToFileFolder = "src/main/resources/";
    private final String fileName = "startups";
    private final ObjectSerializer<SerializableStartUps> objectSerializer =
            new ObjectSerializer<>(pathToFileFolder + fileName);

    public SerializableStartUps loadObject() {
        try {
            return objectSerializer.load();
        } catch (Exception e) {
            objectSerializer.save(new SerializableStartUps());
            return objectSerializer.load();
        }
    }

    public void saveObject(SerializableStartUps obj) {
        objectSerializer.save(obj);
    }
}
