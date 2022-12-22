package project.presentation.controller;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import project.business.facade.TableFacade;
import project.business.models.Table;

public class AllTablesController implements Initializable {


    @FXML
    private TableView<Table> TableTab;

    @FXML
    private TableColumn<Table, Integer> idTable;

    @FXML
    private TableColumn<Table, String> name;

    @FXML
    private TableColumn<Table, String> description;

    @FXML
    private TableColumn<Table, String> booked;

    @Override
    public void initialize(java.net.URL location, java.util.ResourceBundle resources) {

        TableFacade tableFacade = TableFacade.getInstance();

        // Create an array list of all the tables
        ObservableList<Table> tables = tableFacade.getAllTables();

        idTable.setCellValueFactory(new PropertyValueFactory<Table,Integer>("idTable"));
        name.setCellValueFactory(new PropertyValueFactory<Table,String>("name"));
        description.setCellValueFactory(new PropertyValueFactory<Table,String>("description"));
        booked.setCellValueFactory(new PropertyValueFactory<Table,String>("booked"));

        TableTab.setItems(tables);
    }
}
