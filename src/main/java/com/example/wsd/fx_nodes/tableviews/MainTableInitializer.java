package com.example.wsd.fx_nodes.tableviews;

import com.example.wsd.deployables.Deployer;
import com.example.wsd.deployables.StartUp;
import com.example.wsd.deployables.deployable.Deployable;
import com.example.wsd.fx_nodes.popups.StartUpEditorPopUp;
import com.example.wsd.repo.StartUpDataAPI;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.HBox;

import java.io.IOException;
import java.util.List;

public class MainTableInitializer implements TableViewInitializer {

    private final TableView<StartUp> table;
    private final StartUpDataAPI startUpDataAPI = new StartUpDataAPI();

    public MainTableInitializer(TableView<StartUp> table) {
        this.table = table;
    }

    @Override
    public void initializeTable() {

        //Col Init
        TableColumn<StartUp, StartUp> startUpCol = initializeStartUpColumn();
        TableColumn<StartUp, StartUp> actionCol = initializeActionColumn();

        //Col Width
        startUpCol.prefWidthProperty().bind(table.widthProperty().multiply(1.00 / 1.5)); // .6
        actionCol.prefWidthProperty().bind(table.widthProperty().multiply(1.00 / 3)); // .3

        table.getColumns().setAll(List.of(startUpCol, actionCol));
        table.getItems().addAll(startUpDataAPI.getInMemoryStartUps());
    }

    private static TableColumn<StartUp, StartUp> initializeStartUpColumn() {
        TableColumn<StartUp, StartUp> startUpCol = new TableColumn<>("Start Ups");

        startUpCol.setCellValueFactory(p -> new ReadOnlyObjectWrapper<>(p.getValue()));
        startUpCol.setCellFactory(p -> new TableCell<>() {
            @Override
            protected void updateItem(StartUp su, boolean b) {
                super.updateItem(su, b);
                if (su != null) {
                    setText(su.getName());
                } else {
                    setText(null);
                }
            }
        });
        return startUpCol;
    }

    private TableColumn<StartUp, StartUp> initializeActionColumn() {
        TableColumn<StartUp, StartUp> actionCol = new TableColumn<>("Actions");

        actionCol.setCellValueFactory(p -> new ReadOnlyObjectWrapper<>(p.getValue()));
        actionCol.setCellFactory(p -> new TableCell<>() {
            private final Button deployButton = new Button("Deploy");
            private final Button editButton = new Button("Edit");
            private final Button deleteButton = new Button("Delete");
            final HBox hBox = new HBox(deployButton, editButton, deleteButton);

            @Override
            protected void updateItem(StartUp su, boolean b) {
                super.updateItem(su, b);

                if (su != null) {
                    hBox.setSpacing(5);
                    setGraphic(hBox);

                    deployButton.setOnAction(e -> {
                        for (Deployable path : su.getDeployablePaths()) {
                            Deployer.deploy(path);
                        }
                    });

                    editButton.setOnAction(e -> {
                        try {
                            StartUpEditorPopUp.createStartUpPopUp(su, table);
                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        }
                    });

                    deleteButton.setOnAction(e -> {
                        ObservableList<StartUp> items = table.getItems();
                        items.remove(su);
                        startUpDataAPI.saveStartUpsToMemory(items);
                    });

                } else {
                    setGraphic(null);
                }
            }
        });
        return actionCol;
    }
}
