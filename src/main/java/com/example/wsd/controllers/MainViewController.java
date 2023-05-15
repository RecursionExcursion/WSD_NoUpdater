package com.example.wsd.controllers;

import com.example.wsd.HelloApplication;
import com.example.wsd.deployables.StartUp;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainViewController implements Initializable {


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void newButtonClick(ActionEvent actionEvent) {
        System.out.println("New button Click");


        StartUp startUp = new StartUp("New troller");
        try {
            showNewStartUpPopUp(startUp);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }

    private void showNewStartUpPopUp(StartUp startUp) throws IOException {

        Stage popUpWindow = new Stage();

        popUpWindow.initModality(Modality.APPLICATION_MODAL);
        popUpWindow.setTitle("This is a pop up window");

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("new-startup-view.fxml"));

        /*
            Order must be maintained below
         */
        Scene scene = new Scene(fxmlLoader.load(), 400, 300);
        NewStartupViewController controller = fxmlLoader.getController();
        controller.init(startUp);

        popUpWindow.setScene(scene);
        popUpWindow.showAndWait();
    }
}