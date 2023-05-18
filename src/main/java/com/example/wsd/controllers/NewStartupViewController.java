package com.example.wsd.controllers;

import com.example.wsd.deployables.StartUp;
import com.example.wsd.deployables.deploy.Deployable;
import com.example.wsd.deployables.deploy.DeployableFile;
import com.example.wsd.deployables.deploy.DeployableUrl;
import com.example.wsd.fx_nodes.NewStartUpTableInitializer;
import com.example.wsd.models.PathString;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ResourceBundle;

public class NewStartupViewController implements Initializable {
    @FXML
    public Button addRowButton;

    @FXML
    public TableView<PathString> pathTableView;

    @FXML
    public TextField startUpNameTextField;

    private StartUp startUp;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        startUpNameTextField.textProperty().setValue("New Start Up");
        new NewStartUpTableInitializer(pathTableView).initializeTable();
    }

    public void init(StartUp startUp) {
        this.startUp = startUp;
    }

    public void addRowClick() {
        ObservableList<PathString> items = pathTableView.getItems();
        items.add(new PathString());
    }

    public void createStartUpClick() {
        ObservableList<PathString> items = pathTableView.getItems();

        boolean listIsCompletelyValid = true;
        for (PathString i : items) {
            listIsCompletelyValid = testPath(i.getPath());
        }

        if (listIsCompletelyValid) {
            for (PathString i : items) {
                String s = i.getPath();
                startUp.getDeployablePaths().add(getDeployable(s));
            }
            startUp.setName(startUpNameTextField.getText());
            Stage window = (Stage) pathTableView.getScene().getWindow();
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

    private boolean testPath(String path) {
        try {
            //Test if path is valid File Path
            if (new File(path).canExecute()) return true;
            //Check if Path is Valid URL
            new URL(path).toURI();
            return true;
        } catch (MalformedURLException | URISyntaxException e) {
            return false;
        }
    }
}
