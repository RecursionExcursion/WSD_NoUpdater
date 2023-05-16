package com.example.wsd.fx_nodes;

import com.example.wsd.models.PathString;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

public class NewStartUpTableInitializer implements TableViewInitializer {

    private final TableView<PathString> table;

    public NewStartUpTableInitializer(TableView<PathString> table) {
        this.table = table;
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
            private final TextField pathField = new TextField();

            @Override
            protected void updateItem(PathString ps, boolean b) {
                super.updateItem(ps, b);
                if (ps != null) {
                    setGraphic(pathField);
                    pathField.textProperty().setValue(ps.getPath());
                    pathField.textProperty().addListener((observableValue, oldVal, newVal) -> ps.setPath(newVal));
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
                        try {
                            testPath(ps.getPath());
                            System.out.println("Valid path");
                        } catch (URISyntaxException | IOException ex) {
                            System.out.println("Invalid path");
                        }
                    });

                    deleteButton.setOnAction(e -> table.getItems().remove(ps));

                } else {
                    setGraphic(null);
                }
            }
        });


        table.getColumns().setAll(List.of(pathCol, actionCol));
        table.getItems().setAll(List.of(new PathString()));
    }

    private void testPath(String s) throws URISyntaxException, IOException {

        //TODO
        Desktop desktop = Desktop.getDesktop();
        File file = new File(s);
        URI uri = new URI(s);
        boolean b = file.canExecute();
        if (b) desktop.open(file);
        else desktop.browse(new URI(s));
    }
}
