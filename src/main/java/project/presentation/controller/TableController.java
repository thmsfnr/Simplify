package project.presentation.controller;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import java.io.IOException;

/**
 * Created by Simplify members on 22/12/22.
 * This class is the controller of the table frame
 * It is used to display the components of the table frame
 * Il can display the component of AllTablesController, CreateTableController
 * @author Simplify members
 */
public class TableController implements Initializable {

    // the area where to display the components
    @FXML
    private AnchorPane contentArea;


    /**
     * This method is used to initialize the frame
     * It displays the component of CreateTableController by default
     */
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

    /**
     * This method is used to display the component of CreateTableController
     */
    @FXML
    public void CreateTableRoute() throws IOException {
        System.out.println("Create Table button pressed!");
        Parent root = FXMLLoader.load(project.presentation.frame.Table.class.getResource("CreateTableComponent.fxml"));
        contentArea.getChildren().setAll(root);
    }

    /**
     * This method is used to display the component of AllTablesController
     */
    @FXML
    public void getAllTables() throws IOException {
        System.out.println("Get All Tables button pressed!");
        Parent root = FXMLLoader.load(project.presentation.frame.Table.class.getResource("AllTablesComponent.fxml"));
        contentArea.getChildren().setAll(root);
    }
}

