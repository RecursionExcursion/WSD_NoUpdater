package com.example.wsd.controllers;

import com.example.wsd.deployables.StartUp;
import com.example.wsd.deployables.deployable.Deployable;
import com.example.wsd.deployables.deployable.DeployableFile;
import com.example.wsd.deployables.deployable.DeployableUrl;
import com.example.wsd.fx_nodes.tableviews.NewStartUpTableInitializer;
import com.example.wsd.fx_util.PathTester;
import com.example.wsd.models.PathString;
import com.example.wsd.models.StartUpConfirmationWrapper;
import com.example.wsd.repo.StartUpDataAPI;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ResourceBundle;

public class NewStartupViewController implements Initializable {
    @FXML
    public Button addRowButton;
    @FXML
    public Button deleteButton;
    @FXML
    public Button createButton;


    @FXML
    public TableView<PathString> pathTableView;

    @FXML
    public TextField startUpNameTextField;

    private StartUp startUp;
    private StartUpConfirmationWrapper confirmationWrapper;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        startUpNameTextField.textProperty().setValue("New Start Up");
    }

    public void init(StartUpConfirmationWrapper confirmationWrapper) {
        this.confirmationWrapper = confirmationWrapper;
        startUp = confirmationWrapper.getStartUp();
        new NewStartUpTableInitializer(startUp, pathTableView).initializeTable();
        if (startUp.getName() != null) {
            startUpNameTextField.textProperty().setValue(startUp.getName());
        }
        Button[] buttons = {addRowButton, deleteButton, createButton};
        for (Button b : buttons) b.setPrefWidth(80);
    }

    public void addRowClick() {
        ObservableList<PathString> items = pathTableView.getItems();
        items.add(new PathString());
    }

    public void createStartUpClick() {
        ObservableList<PathString> items = pathTableView.getItems();

        boolean listIsCompletelyValid = items.stream()
                                             .map(path -> PathTester.testPath(path.getPath()))
                                             .filter(b -> !b)
                                             .findAny()
                                             .orElse(true);

        if (listIsCompletelyValid) {

            //TODO Code Smell - Clearing list to add back to list?
            startUp.getDeployablePaths().clear();
            items.forEach(ps -> startUp.getDeployablePaths().add(getDeployable(ps.getPath())));

            startUp.setName(startUpNameTextField.getText());

            Stage window = (Stage) pathTableView.getScene().getWindow();
            confirmationWrapper.setConfirmed(true);
            window.close();
        }
    }

    private Deployable getDeployable(String s) {
        try {
            if (new File(s).canExecute()) {
                return new DeployableFile(s);
            } else {
                return new DeployableUrl(s);
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteStartUpClick() {
        Stage window = (Stage) pathTableView.getScene().getWindow();

        StartUpDataAPI startUpDataAPI = new StartUpDataAPI();
        startUpDataAPI.delete(startUp);

        window.close();
    }
}
