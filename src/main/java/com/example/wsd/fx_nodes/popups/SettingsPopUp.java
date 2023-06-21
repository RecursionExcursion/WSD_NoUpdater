package com.example.wsd.fx_nodes.popups;

import com.example.wsd.HelloApplication;
import com.example.wsd.fx_util.GlobalCSS;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class SettingsPopUp {

    public static void createSettingsPopUp() throws IOException {
        Stage settingsWindow = new Stage();

        settingsWindow.initModality(Modality.APPLICATION_MODAL);
        settingsWindow.setTitle("Settings");

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("settings-view.fxml"));

        Scene scene = new Scene(fxmlLoader.load(), 400, 300);

        //Apply CSS to scene
        GlobalCSS.applyGlobalCSS(scene);

        settingsWindow.setScene(scene);
        settingsWindow.setResizable(false);
        settingsWindow.showAndWait();
    }
}
