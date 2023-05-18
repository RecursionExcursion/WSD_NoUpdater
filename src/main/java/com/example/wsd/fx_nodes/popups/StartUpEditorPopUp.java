package com.example.wsd.fx_nodes.popups;

import com.example.wsd.HelloApplication;
import com.example.wsd.controllers.NewStartupViewController;
import com.example.wsd.deployables.StartUp;
import com.example.wsd.repo.StartUpDataAPI;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TableView;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class StartUpEditorPopUp {

    public static void createStartUpPopUp(StartUp startUp, TableView<StartUp> table) throws IOException {
        Stage popUpWindow = new Stage();

        popUpWindow.initModality(Modality.APPLICATION_MODAL);
        popUpWindow.setTitle("Start Up Editor");

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("new-startup-view.fxml"));

        /*
            Order must be maintained below
         */
        Scene scene = new Scene(fxmlLoader.load(), 400, 300);
        NewStartupViewController controller = fxmlLoader.getController();
        controller.init(startUp);

        popUpWindow.setScene(scene);
        popUpWindow.showAndWait();

        //TODO Bug, Start up is created upon exiting popup regardless if submit is pressed, StartUp will not be empty
        // if it is being edited.
        if (!startUp.getDeployablePaths().isEmpty()) {
            ObservableList<StartUp> items = table.getItems();
            items.add(startUp);
            new StartUpDataAPI().saveStartUpsToMemory(items);
        }
    }
}
