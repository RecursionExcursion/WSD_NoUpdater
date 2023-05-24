package com.example.wsd.models;

import com.example.wsd.deployables.StartUp;

public class StartUpConfirmationWrapper {

    private boolean isConfirmed;
    private StartUp startUp;

    public StartUpConfirmationWrapper(StartUp startUp) {
        this.isConfirmed = false;
        this.startUp = startUp;
    }

    public boolean isConfirmed() {
        return isConfirmed;
    }

    public void setConfirmed(boolean confirmed) {
        isConfirmed = confirmed;
    }

    public StartUp getStartUp() {
        return startUp;
    }

    public void setStartUp(StartUp startUp) {
        this.startUp = startUp;
    }
}
