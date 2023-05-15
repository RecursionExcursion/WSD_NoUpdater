package com.example.wsd.fx_nodes;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.util.List;
import java.util.Map;

public class TableInitializer implements TableViewInitializer {

    private final TableView<Map.Entry<Long, String>> table;

    public TableInitializer(TableView<Map.Entry<Long, String>> table, String searchString) {
        this.table = table;
    }

    @Override
    public void initializeTable() {

        TableColumn<Map.Entry<Long, String>, String> mainCol = new TableColumn<>("Title");


        mainCol.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getValue()));
        mainCol.setCellFactory(p -> new TableCell<>() {
            @Override
            protected void updateItem(final String title, boolean empty) {
                super.updateItem(title, empty);
                setText(title);
            }
        });
        mainCol.prefWidthProperty().bind(table.widthProperty().multiply(1.00));

        List<Map.Entry<Long, String>> entries;

        ObservableList<Map.Entry<Long, String>> items = FXCollections.observableArrayList();

        //Setting Nodes
        table.getColumns().add(mainCol);

        table.setItems(items);
    }
}
