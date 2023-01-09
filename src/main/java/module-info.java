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

    opens project.business.models to javafx.base;
    exports project.presentation.controller.table;
    opens project.presentation.controller.table to javafx.fxml;
    exports project.presentation.controller.delivery;
    opens project.presentation.controller.delivery to javafx.fxml;
    exports project.presentation.controller.cart;
    opens project.presentation.controller.cart to javafx.fxml;
    exports project.presentation.controller.event;
    opens project.presentation.controller.event to javafx.fxml;
    exports project.presentation.controller.restaurant;
    opens project.presentation.controller.restaurant to javafx.fxml;
    exports project.presentation.controller.user;
    opens project.presentation.controller.user to javafx.fxml;
    exports project.presentation.controller.meal;
    opens project.presentation.controller.meal to javafx.fxml;
    exports project.presentation.controller.reservation;
    opens project.presentation.controller.reservation to javafx.fxml;
    exports project.presentation.controller.placement;
    opens project.presentation.controller.placement to javafx.fxml;
    exports project.presentation.controller.payment;
    opens project.presentation.controller.payment to javafx.fxml;
    exports project.presentation.controller.opinion;
    opens project.presentation.controller.opinion to javafx.fxml;
    exports project.presentation.controller.notification;
    opens project.presentation.controller.notification to javafx.fxml;
    exports project.presentation.frame.cart;
    opens project.presentation.frame.cart to javafx.fxml;
    exports project.presentation.frame.delivery;
    opens project.presentation.frame.delivery to javafx.fxml;
    exports project.presentation.frame.event;
    opens project.presentation.frame.event to javafx.fxml;
    exports project.presentation.frame.meal;
    opens project.presentation.frame.meal to javafx.fxml;
    exports project.presentation.frame.opinion;
    opens project.presentation.frame.opinion to javafx.fxml;
    opens project.presentation.frame.user to javafx.fxml;
    exports project.presentation.frame.user;
    exports project.presentation.frame.notification;
    opens project.presentation.frame.notification to javafx.fxml;
    exports project.presentation.frame.payment;
    opens project.presentation.frame.payment to javafx.fxml;
    exports project.presentation.frame.reservation;
    opens project.presentation.frame.reservation to javafx.fxml;
    exports project.presentation.frame.placement;
    opens project.presentation.frame.placement to javafx.fxml;
    exports project.presentation.frame.restaurant;
    opens project.presentation.frame.restaurant to javafx.fxml;
    exports project.presentation.frame.table;
    opens project.presentation.frame.table to javafx.fxml;
    exports project.presentation.frame.menu;
    opens project.presentation.frame.menu to javafx.fxml;
    exports project.presentation.controller.menu;
    opens project.presentation.controller.menu to javafx.fxml;
    exports project.utilities;
    opens project.utilities to javafx.fxml;

    exports project;
    opens project to javafx.fxml;
}