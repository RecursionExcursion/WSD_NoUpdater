package com.example.wsd.controllers;

import com.example.wsd.deployables.StartUp;
import com.example.wsd.fx_nodes.popups.StartUpEditorPopUp;
import com.example.wsd.fx_nodes.tableviews.TableInitializer;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainViewController implements Initializable {

    @FXML
    public TableView<StartUp> mainTable;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        new TableInitializer(mainTable).initializeTable();
    }

    public void newButtonClick() {
        try {
            showNewStartUpPopUp(new StartUp());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void showNewStartUpPopUp(StartUp startUp) throws IOException {
        StartUpEditorPopUp.createStartUpPopUp(startUp, mainTable);
    }
}