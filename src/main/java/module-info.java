module com.example.pt2025_30228_iordache_alexandru_assignment_3 {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires java.sql;
    requires java.desktop;

    opens graphicalUserInterface to javafx.fxml;
    exports graphicalUserInterface;
    exports businessLogic;
    exports dataAccess;
    exports model;
    exports connection;
}
