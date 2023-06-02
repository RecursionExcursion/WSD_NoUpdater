package com.example.wsd;

import com.example.wsd.fx_util.CssManager;
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
        scene.getStylesheets().add(CssManager.INSTANCE.getCssUrl());
        stage.setTitle("Workspace Deployer!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void stop() {
        new StartUpDataAPI().update();
    }
}