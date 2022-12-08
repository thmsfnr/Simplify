/**
 * This is the Module for the project to see the requirements
 */
module project.presentation {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires spring.security.crypto;
    requires java.sql;

    exports project.presentation.controller;
    opens project.presentation.controller to javafx.fxml;
    exports project.presentation.frame;
    opens project.presentation.frame to javafx.fxml;

}