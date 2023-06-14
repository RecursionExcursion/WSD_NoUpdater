module com.example.wsd {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires com.fasterxml.jackson.databind;
    /* Do not need if Jlink is not used*/
    requires jdk.crypto.ec;

    opens com.example.wsd.controllers to javafx.fxml;
    exports com.example.wsd;
    exports com.example.wsd.properties to com.fasterxml.jackson.databind;
}