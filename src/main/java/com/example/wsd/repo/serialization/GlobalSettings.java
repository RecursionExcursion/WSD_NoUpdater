package com.example.wsd.repo.serialization;

import java.io.Serializable;

public class GlobalSettings implements Serializable {

    private long loadDelay;
    private boolean alphabetizeStartUps;

    public GlobalSettings() {
        loadDelay = 750;
        alphabetizeStartUps = false;
    }

    public long getLoadDelay() {
        return loadDelay;
    }

    public void setLoadDelay(long loadDelay) {
        this.loadDelay = loadDelay;
    }

    public boolean isAlphabetizeStartUps() {
        return alphabetizeStartUps;
    }

    public void setAlphabetizeStartUps(boolean alphabetizeStartUps) {
        this.alphabetizeStartUps = alphabetizeStartUps;
    }
}
