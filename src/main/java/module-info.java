module com.example.wsd {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;

    opens com.example.wsd.controllers to javafx.fxml;
    exports com.example.wsd;
}