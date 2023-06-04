package com.example.wsd.controllers;

import com.example.wsd.deployables.StartUp;
import com.example.wsd.fx_nodes.popups.SettingsPopUp;
import com.example.wsd.fx_nodes.popups.StartUpEditorPopUp;
import com.example.wsd.fx_nodes.tableviews.MainTableInitializer;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainViewController implements Initializable {

    @FXML
    public TableView<StartUp> mainTable;

    @FXML
    public Button newButton;

    @FXML
    public Button settingsButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        new MainTableInitializer(mainTable).initializeTable();
        //TODO In code button CSS (Code Smell)
        newButton.setPrefWidth(60);
        setGraphicOnSettingsButton();
    }

    private void setGraphicOnSettingsButton() {
        URL resource = getClass().getResource("/images/light_settings_1280.png");
        assert resource != null;
        Image image = new Image(resource.toString(),
                                20, 20, false, true);
        ImageView imageView = new ImageView(image);
        settingsButton.setGraphic(imageView);
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
        new MainTableInitializer(mainTable).initializeTable();

    }

    public void openSettingsMenu() throws IOException {
        SettingsPopUp.createSettingsPopUp();
        new MainTableInitializer(mainTable).initializeTable();

    }
}