package com.example.wsd;

import com.example.wsd.fx_util.GlobalCSS;
import com.example.wsd.properties.PropertyManager;
import com.example.wsd.repo.StartUpDataAPI;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("main-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 600);

        //Apply css to scene
        GlobalCSS.applyGlobalCSS(scene);

        double version = PropertyManager.INSTANCE.getProperties().getVersion();

        stage.setTitle(String.format("Workspace Deployer %.2f", version));
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void stop() {
        StartUpDataAPI.INSTANCE.update();
    }
}