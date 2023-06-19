package com.example.wsd.controllers;

import com.example.wsd.repo.SettingsDataAPI;
import com.example.wsd.repo.serialization.GlobalSettings;
import com.example.wsd.update.UpdateManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

public class SettingsViewController implements Initializable {

    private final SettingsDataAPI settingsAPI = SettingsDataAPI.INSTANCE;

    @FXML
    public Label loadDelayLabel;
    @FXML
    public TextField loadDelayField;

    @FXML
    public CheckBox sortAlphabeticallyCheckBox;

    @FXML
    public Label errorLabel;

    @FXML
    public Button saveButton;

    @FXML
    public Button cancelButton;

    @FXML
    public Button resetButton;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Button[] buttons = {saveButton, cancelButton, resetButton};
        for (Button b : buttons) b.setPrefWidth(60);

        //Set Tool Tips
        loadDelayLabel.setTooltip(getLoadDelayToolTip());
        loadDelayField.setTooltip(getLoadDelayToolTip());

        loadSettingsToNodes();
    }

    private Tooltip getLoadDelayToolTip() {
        Tooltip tooltip = new Tooltip();
        tooltip.textProperty().setValue("Delays start up processes to account for browser spin up time. " +
                                                "Decreasing this variable may negativity impact " +
                                                "performance on slower machines. \n" +
                                                "Default- 750 ms.");
        tooltip.setPrefWidth(200);
        tooltip.setWrapText(true);
        tooltip.setShowDuration(Duration.seconds(15));
        tooltip.setStyle("-fx-font-size: 12");
        return tooltip;
    }

    public void saveAndCloseClick(ActionEvent actionEvent) {
        try {
            long longDelay = Long.parseLong(loadDelayField.textProperty().get());
            if (longDelay < 0) {
                throw new NumberFormatException();
            }

            GlobalSettings settings = settingsAPI.read();
            settings.setLoadDelay(longDelay);
            settings.setAlphabetizeStartUps(sortAlphabeticallyCheckBox.isSelected());
            settingsAPI.update();

            closeWindowFromEvent(actionEvent);
        } catch (NumberFormatException e) {
            errorLabel.setText("Delay must be a positive whole number!");
            errorLabel.setTextFill(Color.RED);
            errorLabel.setVisible(true);
        }
    }

    public void cancelAndCloseClick(ActionEvent actionEvent) {
        closeWindowFromEvent(actionEvent);
    }

    private void closeWindowFromEvent(ActionEvent actionEvent) {
        Node source = (Node) actionEvent.getSource();
        Stage window = (Stage) source.getScene().getWindow();
        window.close();
    }

    private void loadSettingsToNodes() {
        loadDelayField.textProperty().setValue(String.valueOf(settingsAPI.read().getLoadDelay()));
        sortAlphabeticallyCheckBox.selectedProperty().setValue(settingsAPI.read().isAlphabetizeStartUps());
    }

    public void resetButtonClick() {
        settingsAPI.reset();
        loadSettingsToNodes();
    }

    public void checkForUpdateClick() {
        new UpdateManager().update();
    }
}
