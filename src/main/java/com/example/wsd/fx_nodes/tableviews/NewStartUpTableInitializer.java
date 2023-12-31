package com.example.wsd.fx_nodes.tableviews;

import com.example.wsd.deployables.Deployer;
import com.example.wsd.deployables.StartUp;
import com.example.wsd.deployables.deployable.Deployable;
import com.example.wsd.deployables.deployable.DeployableFile;
import com.example.wsd.deployables.deployable.DeployableUrl;
import com.example.wsd.fx_nodes.ButtonFactory;
import com.example.wsd.fx_nodes.HBoxFactory;
import com.example.wsd.fx_util.DeployablePathBuilder;
import com.example.wsd.models.PathString;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;


public class NewStartUpTableInitializer implements TableViewInitializer {

    private final TableView<PathString> table;
    private final List<PathString> pathStrings;

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
        TableColumn<PathString, PathString> pathCol = initializePathColumn();
        TableColumn<PathString, PathString> actionCol = initializeActionColumn();

        //Column Width
        pathCol.prefWidthProperty().bind(table.widthProperty().multiply(.6)); // .6
        actionCol.prefWidthProperty().bind(table.widthProperty().multiply(.4)); // .3

        table.getColumns().setAll(List.of(pathCol, actionCol));

        if (pathStrings.isEmpty()) {
            table.getItems().setAll(List.of(new PathString()));
        } else {
            table.getItems().setAll(pathStrings);
        }
    }

    private TableColumn<PathString, PathString> initializePathColumn() {
        TableColumn<PathString, PathString> pathCol = new TableColumn<>("Path");
        pathCol.setCellValueFactory(p -> new ReadOnlyObjectWrapper<>(p.getValue()));
        pathCol.setCellFactory(p -> new TableCell<>() {
            @Override
            protected void updateItem(PathString ps, boolean b) {
                final TextField pathTextField = new TextField();

                Consumer<String> setPathFieldStyle = s -> {
                    pathTextField.getStyleClass().clear();
                    pathTextField.getStyleClass().add(s);
                };

                setPathFieldStyle.accept("red-border");
                pathTextField.textProperty().addListener((ob, ov, nv) -> {
                    if (DeployablePathBuilder.testPath(nv)) {
                        setPathFieldStyle.accept("green-border");
                    } else {
                        setPathFieldStyle.accept("red-border");
                    }
                });

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
        return pathCol;
    }

    private TableColumn<PathString, PathString> initializeActionColumn() {
        TableColumn<PathString, PathString> actionCol = new TableColumn<>("Actions");
        actionCol.setCellValueFactory(p -> new ReadOnlyObjectWrapper<>(p.getValue()));
        actionCol.setCellFactory(p -> new TableCell<>() {
            private final Button testButton = ButtonFactory.createButton("Test");
            private final Button deleteButton = ButtonFactory.createButton("Delete");
            final HBox hBox = HBoxFactory.createHbox(testButton, deleteButton);

            @Override
            protected void updateItem(PathString ps, boolean b) {
                super.updateItem(ps, b);

                if (ps != null) {
                    hBox.setSpacing(5);
                    setGraphic(hBox);
                    testButton.setOnAction(e -> {
                        String path = ps.getPath();
                        if (DeployablePathBuilder.testPath(path)) {
                            Deployable deployable = DeployablePathBuilder.buildDeployable(path);
                            Deployer.deploy(deployable);
                        }
                    });
                    deleteButton.setOnAction(e -> table.getItems().remove(ps));

                } else {
                    setGraphic(null);
                }
            }
        });
        return actionCol;
    }
}
