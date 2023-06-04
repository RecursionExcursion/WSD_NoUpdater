package com.example.wsd.controllers;

import com.example.wsd.repo.SettingsDataAPI;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

public class SettingsViewController implements Initializable {

    @FXML
    public Label loadDelayLabel;
    @FXML
    public TextField loadDelayField;

    @FXML
    public Button saveButton;

    @FXML
    public Button cancelButton;

    @FXML
    public Label errorLabel;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        saveButton.setPrefWidth(60);
        cancelButton.setPrefWidth(60);
        //Set Tool Tips
        loadDelayLabel.setTooltip(getLoadDelayToolTip());
        loadDelayField.setTooltip(getLoadDelayToolTip());

        long loadDelay = SettingsDataAPI.INSTANCE.read().getLoadDelay();
        loadDelayField.textProperty().setValue(String.valueOf(loadDelay));
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
            SettingsDataAPI.INSTANCE.read().setLoadDelay(longDelay);
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
}
