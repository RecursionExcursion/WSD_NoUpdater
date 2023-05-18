package com.example.wsd.fx_nodes.tableviews;

import com.example.wsd.deployables.StartUp;
import com.example.wsd.deployables.deploy.Deployable;
import com.example.wsd.deployables.deploy.DeployableFile;
import com.example.wsd.deployables.deploy.DeployableUrl;
import com.example.wsd.models.PathString;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class NewStartUpTableInitializer implements TableViewInitializer {

    private final TableView<PathString> table;
    private final List<PathString> pathStrings;

    public NewStartUpTableInitializer(TableView<PathString> table) {
        this.table = table;
        pathStrings = List.of(new PathString());
    }

    public NewStartUpTableInitializer(StartUp startUp, TableView<PathString> table) {
        this.table = table;
        List<PathString> pathStrings = new ArrayList<>();
        for (Deployable deployablePath : startUp.getDeployablePaths()) {

            String s = deployablePath instanceof DeployableFile ?
                    ((DeployableFile) deployablePath).getFile().toString() :
                    ((DeployableUrl) deployablePath).getURL().toString();

            PathString ps = new PathString();
            ps.setPath(s);
            pathStrings.add(ps);
        }
        this.pathStrings = pathStrings;
    }

    @Override
    public void initializeTable() {

        //Column Init
        TableColumn<PathString, PathString> pathCol = new TableColumn<>("Path");
        TableColumn<PathString, PathString> actionCol = new TableColumn<>("Actions");

        //Column Width
        pathCol.prefWidthProperty().bind(table.widthProperty().multiply(1.00 / 2)); // .6
        actionCol.prefWidthProperty().bind(table.widthProperty().multiply(1.00 / 2)); // .3

        //Column Set up
        pathCol.setCellValueFactory(p -> new ReadOnlyObjectWrapper<>(p.getValue()));
        pathCol.setCellFactory(p -> new TableCell<>() {
            private final TextField pathTextField = new TextField();

            @Override
            protected void updateItem(PathString ps, boolean b) {
                super.updateItem(ps, b);
                if (ps != null) {
                    setGraphic(pathTextField);
                    pathTextField.textProperty().setValue(ps.getPath());
                    pathTextField.textProperty().addListener((observableValue, oldVal, newVal) -> ps.setPath(newVal));
                } else {
                    setGraphic(null);
                }
            }
        });

        actionCol.setCellValueFactory(p -> new ReadOnlyObjectWrapper<>(p.getValue()));
        actionCol.setCellFactory(p -> new TableCell<>() {
            private final Button testButton = new Button("Test");
            private final Button deleteButton = new Button("Delete");
            final HBox hBox = new HBox(testButton, deleteButton);

            @Override
            protected void updateItem(PathString ps, boolean b) {
                super.updateItem(ps, b);

                if (ps != null) {
                    hBox.setSpacing(5);
                    setGraphic(hBox);

                    testButton.setOnAction(e -> {
                        System.out.printf("Testing... %s%n", ps.getPath());

                        boolean isValid = testPath(ps.getPath());
                        if (isValid) {
                            System.out.printf("%s is valid%n", ps.getPath());
                        } else {
                            System.out.printf("%s is not valid%n", ps.getPath());
                        }
                    });

                    deleteButton.setOnAction(e -> table.getItems().remove(ps));

                } else {
                    setGraphic(null);
                }
            }
        });


        table.getColumns().setAll(List.of(pathCol, actionCol));

        if(pathStrings.isEmpty()){
            table.getItems().setAll(List.of(new PathString()));
        }else  {
            table.getItems().setAll(pathStrings);
        }
    }

    //TODO Fix testing logic (drop button and place in update lambda?)
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
