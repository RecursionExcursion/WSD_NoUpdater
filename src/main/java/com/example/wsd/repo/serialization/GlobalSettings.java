package com.example.wsd.repo.serialization;

import java.io.Serializable;

public class GlobalSettings implements Serializable {

    private long loadDelay;

    public GlobalSettings(long loadDelay) {
        this.loadDelay = loadDelay;
    }

    public long getLoadDelay() {
        return loadDelay;
    }

    public void setLoadDelay(long loadDelay) {
        this.loadDelay = loadDelay;
    }
}
