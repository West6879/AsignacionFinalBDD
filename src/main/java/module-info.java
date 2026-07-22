module bddAcademico {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires java.sql;
    requires javafx.base;
    requires javafx.graphics;

    opens estructura to javafx.base;
    exports estructura;

    opens visual to javafx.fxml;
    exports visual;
}