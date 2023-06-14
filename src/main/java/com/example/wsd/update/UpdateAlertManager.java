package com.example.wsd.update;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.io.IOException;
import java.util.Optional;

public class UpdateAlertManager {

   private final ButtonType update = new ButtonType("Update");
   private final ButtonType cancel = new ButtonType("Cancel");

   public void showAlert(){
       Optional<ButtonType> result = createAlert().showAndWait();
       if (result.isPresent() && result.orElse(cancel) == update) {
           System.out.println("Update that shit");
           try {
               new UpdateManager().deployUpdater();
           } catch (IOException e) {
               throw new RuntimeException(e);
           }
       }
   }

    private Alert createAlert(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "A new update is available!", update, cancel);
        alert.setHeaderText(null);
        return alert;
    }
}
