package com.example.wsd.controllers;

import com.example.wsd.HelloApplication;
import com.example.wsd.deployables.StartUp;
import com.example.wsd.fx_nodes.TableInitializer;
import com.example.wsd.repo.StartUpDataAPI;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.TableView;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainViewController implements Initializable {

    @FXML
    public TableView<StartUp> mainTable;

    private final StartUpDataAPI startUpDataAPI = new StartUpDataAPI();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        new TableInitializer(mainTable).initializeTable();
    }

    public void newButtonClick(ActionEvent actionEvent) {
        System.out.println("New button Click");


        StartUp startUp = new StartUp("New SU");
        try {
            showNewStartUpPopUp(startUp);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }

    private void showNewStartUpPopUp(StartUp startUp) throws IOException {

        Stage popUpWindow = new Stage();

        popUpWindow.initModality(Modality.APPLICATION_MODAL);
        popUpWindow.setTitle("Start Up Creator");

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("new-startup-view.fxml"));

        /*
            Order must be maintained below
         */
        Scene scene = new Scene(fxmlLoader.load(), 400, 300);
        NewStartupViewController controller = fxmlLoader.getController();
        controller.init(startUp);

        popUpWindow.setScene(scene);
        popUpWindow.showAndWait();

        if (!startUp.getDeployablePaths().isEmpty()) {
            ObservableList<StartUp> items = mainTable.getItems();
            items.add(startUp);
            startUpDataAPI.saveStartUpsToMemory(items);
        }
    }
}