module RMS {

    requires javafx.graphics;
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.desktop;

    opens View;
    opens Controller;
    opens rms;
    opens Model;
}