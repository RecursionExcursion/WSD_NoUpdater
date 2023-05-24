module com.example.wsd {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens com.example.wsd to javafx.fxml;
    exports com.example.wsd;
    exports com.example.wsd.models;
    exports com.example.wsd.controllers;
    exports com.example.wsd.deployables;
    exports com.example.wsd.deployables.deploy;
    opens com.example.wsd.controllers to javafx.fxml;
}