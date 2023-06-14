package com.example.wsd.properties;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URL;


public enum PropertyManager {

    INSTANCE;

    private final URL resource = getClass().getResource("/properties/properties.json");
    private final Properties properties = getRepo(resource);

    private Properties getRepo(URL jsonUrl) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(jsonUrl, Properties.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Properties getProperties() {
        return properties;
    }
}