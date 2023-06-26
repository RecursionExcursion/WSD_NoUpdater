package com.example.wsd.update;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Optional;

class UpdateAlertManager {

    private static final ButtonType update = new ButtonType("Go to Releases Download");
    private static final ButtonType cancel = new ButtonType("Cancel");

    static boolean showUpdateAlert() {
        Optional<ButtonType> result = createNewUpdateAlert().showAndWait();
        return result.isPresent() && result.orElse(cancel) == update;
    }

    static void showNoUpdateAlert() {
        createNoUpdateAlert().showAndWait();
    }

    private static Alert createNewUpdateAlert() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "A new update is available!", update, cancel);
        alert.setHeaderText(null);
        return alert;
    }

    private static Alert createNoUpdateAlert() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "You are up to date!");
        alert.setHeaderText(null);
        return alert;
    }
}
