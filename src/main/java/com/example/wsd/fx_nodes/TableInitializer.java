package com.example.wsd.fx_nodes;

import com.example.wsd.deployables.Deployer;
import com.example.wsd.deployables.StartUp;
import com.example.wsd.deployables.deploy.Deployable;
import com.example.wsd.repo.StartUpDataAPI;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.HBox;

import java.util.List;

public class TableInitializer implements TableViewInitializer {

    private final TableView<StartUp> table;
    private final StartUpDataAPI startUpDataAPI = new StartUpDataAPI();

    public TableInitializer(TableView<StartUp> table) {
        this.table = table;
    }

    @Override
    public void initializeTable() {

        TableColumn<StartUp, StartUp> startUpCol = new TableColumn<>("Start Ups");
        TableColumn<StartUp, StartUp> actionCol = new TableColumn<>("Actions");

        startUpCol.prefWidthProperty().bind(table.widthProperty().multiply(1.00 / 1.5)); // .6
        actionCol.prefWidthProperty().bind(table.widthProperty().multiply(1.00 / 3)); // .3

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


        table.getColumns().setAll(List.of(startUpCol, actionCol));
        table.getItems().addAll(startUpDataAPI.getInMemoryStartUps());
    }
}
