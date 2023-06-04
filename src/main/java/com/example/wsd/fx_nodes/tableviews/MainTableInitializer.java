package com.example.wsd.fx_nodes.tableviews;

import com.example.wsd.deployables.Deployer;
import com.example.wsd.deployables.StartUp;
import com.example.wsd.deployables.deployable.Deployable;
import com.example.wsd.fx_nodes.ButtonFactory;
import com.example.wsd.fx_nodes.HBoxFactory;
import com.example.wsd.fx_nodes.popups.StartUpEditorPopUp;
import com.example.wsd.repo.SettingsDataAPI;
import com.example.wsd.repo.StartUpDataAPI;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.concurrent.Task;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.HBox;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class MainTableInitializer implements TableViewInitializer {

    private final TableView<StartUp> table;
    private final StartUpDataAPI startUpDataAPI = StartUpDataAPI.INSTANCE;

    public MainTableInitializer(TableView<StartUp> table) {
        this.table = table;
    }

    @Override
    public void initializeTable() {

        //Col Init
        TableColumn<StartUp, StartUp> startUpCol = initializeStartUpColumn();
        TableColumn<StartUp, StartUp> actionCol = initializeActionColumn();

        //Col Width
        startUpCol.prefWidthProperty().bind(table.widthProperty().multiply(.66)); // .6
        actionCol.prefWidthProperty().bind(table.widthProperty().multiply(.33)); // .3

        table.getColumns().setAll(List.of(startUpCol, actionCol));


        List<StartUp> startUpsCopy = new ArrayList<>(startUpDataAPI.read());

        //TODO ensure all later refrences are from local memory

        if(SettingsDataAPI.INSTANCE.read().isAlphabetizeStartUps()){
            startUpsCopy.sort(Comparator.comparing(a -> a.getName().toLowerCase()));
        }
        table.setItems(FXCollections.observableList(startUpsCopy));
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
            private final Button deployButton = ButtonFactory.createButton("Deploy");
            private final Button editButton = ButtonFactory.createButton("Edit");
            private final Button deleteButton = ButtonFactory.createButton("Delete");
            final HBox hBox = HBoxFactory.createHbox(deployButton, editButton, deleteButton);

            @Override
            protected void updateItem(StartUp su, boolean b) {
                super.updateItem(su, b);

                if (su != null) {
                    setGraphic(hBox);

                    deployButton.setOnAction(e -> {


                        List<Deployable> deployablePaths = su.getDeployablePaths();
                        if (!deployablePaths.isEmpty()) {
                            Task<Void> deploymentTask = new Task<>() {
                                @Override
                                protected Void call() throws Exception {
                                    for (Deployable path : deployablePaths) {
                                        //Sleep is to take browser initialization into account
                                        TimeUnit.MILLISECONDS.sleep(SettingsDataAPI.INSTANCE.read().getLoadDelay());
                                        Deployer.deploy(path);
                                    }
                                    return null;
                                }
                            };
                            new Thread(deploymentTask).start();
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
                        startUpDataAPI.delete(su);
                        table.setItems(FXCollections.observableList(startUpDataAPI.read()));
                    });
                } else {
                    setGraphic(null);
                }
            }
        });
        return actionCol;
    }
}
