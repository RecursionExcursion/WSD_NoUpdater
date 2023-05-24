package com.example.wsd.fx_nodes.popups;

import com.example.wsd.HelloApplication;
import com.example.wsd.controllers.NewStartupViewController;
import com.example.wsd.deployables.StartUp;
import com.example.wsd.models.StartUpConfirmationWrapper;
import com.example.wsd.repo.StartUpDataAPI;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TableView;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class StartUpEditorPopUp {

    /** Creates a smaller window to edit StartUp Objects.
     *
     * <p> Creates a smaller window that takes URLs and FilePaths.
     * The wrapper object is used for validation upon the user
     * click of the 'Create' button. </p>
     *
     * @param startUp the StartUp object to be edited
     * @param table the table that will have the StartUp added to, as well as will be written into memory
     */

    public static void createStartUpPopUp(StartUp startUp, TableView<StartUp> table) throws IOException {
        Stage popUpWindow = new Stage();


        StartUpConfirmationWrapper confirmationWrapper = new StartUpConfirmationWrapper(startUp);

        popUpWindow.initModality(Modality.APPLICATION_MODAL);
        popUpWindow.setTitle("Start Up Editor");

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("new-startup-view.fxml"));

        /*
            Order must be maintained below
         */
        Scene scene = new Scene(fxmlLoader.load(), 400, 300);
        NewStartupViewController controller = fxmlLoader.getController();
        controller.init(confirmationWrapper);

        popUpWindow.setScene(scene);
        popUpWindow.showAndWait();

        if (confirmationWrapper.isConfirmed()) {
            ObservableList<StartUp> items = table.getItems();
            if(!items.contains(startUp)){
                items.add(startUp);
            }
            new StartUpDataAPI().saveStartUpsToMemory(items);
            table.refresh();
        }
    }
}
