package project.presentation.controller;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Window;
import project.business.facade.TableFacade;
import project.business.models.Table;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;




import java.io.IOException;
import java.util.ArrayList;

import static project.presentation.controller.LoginController.infoBox;
import static project.presentation.controller.LoginController.showAlert;


public class TableController implements Initializable {

    @FXML
    private AnchorPane contentArea;

    @Override
    public void initialize(java.net.URL location, java.util.ResourceBundle resources) {

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

