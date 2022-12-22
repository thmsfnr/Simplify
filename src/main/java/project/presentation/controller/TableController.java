package project.presentation.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
public class TableController implements Initializable {

    @FXML
    private AnchorPane contentArea;

    @Override
    public void initialize(java.net.URL location, java.util.ResourceBundle resources) {
        Parent root = null;
        try {
            root = FXMLLoader.load(project.presentation.frame.Table.class.getResource("CreateTableComponent.fxml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        contentArea.getChildren().setAll(root);
    }

    @FXML
    public void CreateTableRoute(ActionEvent event) throws IOException {
        System.out.println("Create Table button pressed!");
        Parent root = FXMLLoader.load(project.presentation.frame.Table.class.getResource("CreateTableComponent.fxml"));
        contentArea.getChildren().setAll(root);
    }

    @FXML
    public void getAllTables(ActionEvent event) throws IOException {
        System.out.println("Get All Tables button pressed!");
        Parent root = FXMLLoader.load(project.presentation.frame.Table.class.getResource("AllTablesComponent.fxml"));
        contentArea.getChildren().setAll(root);
    }
}

