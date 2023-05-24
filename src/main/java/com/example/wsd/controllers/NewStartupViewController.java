package com.example.wsd.controllers;

import com.example.wsd.deployables.StartUp;
import com.example.wsd.deployables.deploy.Deployable;
import com.example.wsd.deployables.deploy.DeployableFile;
import com.example.wsd.deployables.deploy.DeployableUrl;
import com.example.wsd.fx_nodes.tableviews.NewStartUpTableInitializer;
import com.example.wsd.fx_util.PathTester;
import com.example.wsd.models.PathString;
import com.example.wsd.models.StartUpConfirmationWrapper;
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
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;

public class NewStartupViewController implements Initializable {
    @FXML
    public Button addRowButton;

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

    }

    public void addRowClick() {
        ObservableList<PathString> items = pathTableView.getItems();
        items.add(new PathString());
    }

    public void createStartUpClick() {
        ObservableList<PathString> items = pathTableView.getItems();
        Set<PathString> pathStringSet = new HashSet<>(items);

        boolean listIsCompletelyValid = true;
        for (PathString i : pathStringSet) {
            listIsCompletelyValid = PathTester.testPath(i.getPath());
        }

        if (listIsCompletelyValid) {
            for (PathString i : pathStringSet) {
                String s = i.getPath();
                startUp.getDeployablePaths().add(getDeployable(s));
            }

            //TODO Code Smell - Clearing list to add set back into list
            startUp.setName(startUpNameTextField.getText());
            startUp.getDeployablePaths().clear();
            pathStringSet.forEach(ps -> startUp.getDeployablePaths().add(getDeployable(ps.getPath())));

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
}
