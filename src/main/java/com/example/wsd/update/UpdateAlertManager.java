package com.example.wsd.update;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Optional;

class UpdateAlertManager {

    private final ButtonType update = new ButtonType("Update and Restart");
    private final ButtonType cancel = new ButtonType("Cancel");
    private final UpdateManager updateManager;

    UpdateAlertManager(UpdateManager manager) {
        updateManager = manager;
    }

    boolean showUpdateAlert() {

        Optional<ButtonType> result = createNewUpdateAlert().showAndWait();
        if (result.isPresent() && result.orElse(cancel) == update) {
            System.out.println("Update that shit");
            return true;

        }

        return false;
    }

    void showNoUpdateAlert() {
        createNoUpdateAlert().showAndWait();
    }

    private Alert createNewUpdateAlert() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "A new update is available!", update, cancel);
        alert.setHeaderText(null);
        return alert;
    }

    private Alert createNoUpdateAlert() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "You are up to date");
        alert.setHeaderText(null);
        return alert;
    }
}
