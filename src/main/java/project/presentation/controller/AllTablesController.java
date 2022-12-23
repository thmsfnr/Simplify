package project.presentation.controller;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.stage.Window;
import project.business.facade.TableFacade;
import project.business.models.Table;
import javafx.event.ActionEvent;
import javafx.util.Callback;
import static project.presentation.controller.Display.infoBox;
import static project.presentation.controller.Display.showAlert;

/**
 * Created by Simplify members on 22/12/22.
 * This class is the controller of the AllTablesComponent
 * It is used to communicate with the business layer
 * It renders the view of all the tables in the database
 * the manager can delete or update a table and See the details of a table
 * @author Simplify members
 */
public class AllTablesController implements Initializable {

    // The table that will contain all the tables
    @FXML
    private TableView<Table> TableTab;


    // The column that will contain the id of the table
    @FXML
    private TableColumn<Table, Integer> idTable;

    // The column that will contain the name of the table
    @FXML
    private TableColumn<Table, String> name;

    // The column that will contain the description of the table
    @FXML
    private TableColumn<Table, String> description;

    // The column that will contain the information about the booking of the table
    @FXML
    private TableColumn<Table, String> booked;




    /**
     * This method is used to add a colomn to the table that will contain the button to update a table
     * A new frame will pop up when the button is clicked
     */
    private void addUpdateButtonToTable() {
        // Create a new column
        TableColumn<Table, Void> colBtn = new TableColumn("Update");

        // Add the button to the column
        Callback<TableColumn<Table, Void>, TableCell<Table, Void>> cellFactory = new Callback<TableColumn<Table, Void>, TableCell<Table, Void>>() {
            @Override
            public TableCell<Table, Void> call(final TableColumn<Table, Void> param) {
                final TableCell<Table, Void> cell = new TableCell<Table, Void>() {

                    private final Button btn = new Button();
                    {
                        btn.setOnAction((ActionEvent event) -> {
                            Table data = getTableView().getItems().get(getIndex());
                            System.out.println("selectedData: " + data);
                        });
                    }

                    // The button will be displayed in the table
                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            // The button will be displayed with an image
                            Image image = new Image("file:src/main/resources/project/presentation/frame/update.png");
                            ImageView imageview = new ImageView(image);
                            imageview.setFitHeight(20);
                            imageview.setPreserveRatio(true);

                            btn.setPrefSize(50, 50);
                            //Setting a graphic to the button
                            btn.setGraphic(imageview);
                            btn.setStyle("-fx-background-color: transparent; -fx-cursor: hand; -fx-alignment: center; -fx-border-color: #c8ded0; -fx-border-width: 1px; -fx-border-radius: 5px;");
                            btn.setId("updateBtn");

                            // When the button is clicked, a new frame will pop up
                            btn.setOnAction((ActionEvent event) -> {
                                Table data = getTableView().getItems().get(getIndex());
                                updateTable(event, data);
                            });

                            setGraphic(btn);
                        }
                    }
                };
                return cell;
            }
        };

        // Add the column to the table
        colBtn.setCellFactory(cellFactory);

        TableTab.getColumns().add(colBtn);

    }

    /**
     * This Method is usef to handle the event of clicking on the update button
     * A new frame will pop up
     * @param event the event of clicking on the update button
     * @param data the table that will be updated
     */
    public void updateTable(ActionEvent event, Table data)
    {
        try{
            // Load the fxml file and create a new stage for the popup
            FXMLLoader loader = new FXMLLoader(project.presentation.frame.Table.class.getResource("TableDetailsUpdate.fxml"));
            Parent root = loader.load();
            DetailsTableUpdateController controller = loader.getController();
            controller.setTextFields(data);
            Stage stage = new Stage();
            stage.setTitle("Update Table");
            stage.setScene(new Scene(root));
            stage.show();

            // Close the frame and reload the table
            stage.setOnCloseRequest(e -> {
                refreshTable();
            });

        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * This method is used to add a colomn to the table that will contain the button to delete a table
     */
    private void addDeleteButtonToTable() {
        // Create a new column
        TableColumn<Table, Void> colBtn = new TableColumn("Delete");

        // Add the button to the column
        Callback<TableColumn<Table, Void>, TableCell<Table, Void>> cellFactory = new Callback<TableColumn<Table, Void>, TableCell<Table, Void>>() {
            @Override
            public TableCell<Table, Void> call(final TableColumn<Table, Void> param) {
                final TableCell<Table, Void> cell = new TableCell<Table, Void>() {

                    private final Button btn = new Button();
                    {
                        btn.setOnAction((ActionEvent event) -> {
                            Table data = getTableView().getItems().get(getIndex());
                            System.out.println("selectedData: " + data);
                        });
                    }

                    // The button will be displayed in the table

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            // The button will be displayed with an image
                            Image image = new Image("file:src/main/resources/project/presentation/frame/delete.png");
                            ImageView imageview = new ImageView(image);
                            imageview.setFitHeight(20);
                            imageview.setPreserveRatio(true);

                            btn.setPrefSize(50, 50);
                            //Setting a graphic to the button
                            btn.setGraphic(imageview);
                            btn.setStyle("-fx-background-color: transparent; -fx-cursor: hand; -fx-alignment: center; -fx-border-color: red; -fx-border-width: 1px; -fx-border-radius: 5px;");
                            btn.setId("deletebtn");


                            btn.setOnAction((ActionEvent event) -> {
                                Table data = getTableView().getItems().get(getIndex());
                                deleteTable(event, data);
                            });

                            setGraphic(btn);
                        }
                    }
                };
                return cell;
            }
        };

        // Add the column to the table
        colBtn.setCellFactory(cellFactory);

        TableTab.getColumns().add(colBtn);

    }

    /**
     * This Method is usef to handle the event of clicking on the delete button
     * @param event the event of clicking on the delete button
     * @param data the table that will be deleted
     */
    public void deleteTable(ActionEvent event, Table data) {

        // Create a new alert to confirm the deletion
        Window owner = TableTab.getScene().getWindow();

        // Call the facade to delete the table from the database
        TableFacade tableFacade = TableFacade.getInstance();
        Boolean result = tableFacade.deleteTable(data.getIdTable());
        if (result) {
            infoBox("Table deleted successfully!", null, "Success");
        } else {
            showAlert(Alert.AlertType.ERROR, owner, "Form Error!",
                    "Please enter your name");
            return;
        }
        // Refresh the table
        refreshTable();
    }

    /**
     * This method is used to refresh the table
     * It will be called after an update or a deletion
     */
    public void refreshTable() {
        // Call the facade to get
        TableFacade tableFacade = TableFacade.getInstance();
        ObservableList<Table> tables = tableFacade.getAllTables();
        // Set the table
        TableTab.setItems(tables);
    }


    /**
     * This method is used to add a colomn to the table that will contain the button to see all the details of a table
     */
    private void addDetailsButtonToTable() {

        TableColumn<Table, Void> colBtn = new TableColumn("Details");
        Callback<TableColumn<Table, Void>, TableCell<Table, Void>> cellFactory = new Callback<TableColumn<Table, Void>, TableCell<Table, Void>>() {
            @Override
            public TableCell<Table, Void> call(final TableColumn<Table, Void> param) {
                final TableCell<Table, Void> cell = new TableCell<Table, Void>() {

                    private final Button btn = new Button();
                    {
                        btn.setOnAction((ActionEvent event) -> {
                            Table data = getTableView().getItems().get(getIndex());
                            System.out.println("selectedData: " + data);
                        });
                    }


                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            Image image = new Image("file:src/main/resources/project/presentation/frame/details.png");
                            ImageView imageview = new ImageView(image);
                            imageview.setFitHeight(20);
                            imageview.setPreserveRatio(true);

                            btn.setPrefSize(50, 50);
                            //Setting a graphic to the button
                            btn.setGraphic(imageview);
                            btn.setStyle("-fx-background-color: transparent; -fx-cursor: hand; -fx-alignment: center; -fx-border-color: skyblue; -fx-border-width: 1px; -fx-border-radius: 5px;");
                            btn.setId("detailsbtn");
                            btn.setOnAction((ActionEvent event) -> {
                                Table data = getTableView().getItems().get(getIndex());
                                detailsTable(event, data);
                            });

                            setGraphic(btn);
                        }
                    }
                };
                return cell;
            }
        };

        colBtn.setCellFactory(cellFactory);

        TableTab.getColumns().add(colBtn);

    }

    /**
     * This method is used to handle the event of clicking on the details button
     * @param event the event of clicking on the details button
     * @param table the table that will be deleted
     */
    public void detailsTable(ActionEvent event, Table table)
    {
        try{
            FXMLLoader loader = new FXMLLoader(project.presentation.frame.Table.class.getResource("TableDetails.fxml"));
            Parent root = loader.load();
            DetailsTableController controller = loader.getController();

            TableFacade tableFacade = TableFacade.getInstance();
            Table data = tableFacade.getTableById(table.getIdTable());
            controller.setFields(data);
            Stage stage = new Stage();
            stage.setTitle("Update Table");
            stage.setScene(new Scene(root));
            stage.show();

            // Si on ferme la fenetre on recharge la table
            stage.setOnCloseRequest(e -> {
                refreshTable();
            });

        } catch(Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * This method is used to initialize the table controller to display the table
     */
    public void initialize(java.net.URL location, java.util.ResourceBundle resources) {

        TableFacade tableFacade = TableFacade.getInstance();

        // Create an array list of all the tables
        ObservableList<Table> tables = tableFacade.getAllTables();

        // Set the table
        idTable.setCellValueFactory(new PropertyValueFactory<Table, Integer>("idTable"));
        name.setCellValueFactory(new PropertyValueFactory<Table, String>("name"));
        description.setCellValueFactory(new PropertyValueFactory<Table, String>("description"));
        booked.setCellValueFactory(new PropertyValueFactory<Table, String>("booked"));
        TableTab.setItems(tables);

        // Add the buttons to the table
        addUpdateButtonToTable();
        addDeleteButtonToTable();
        addDetailsButtonToTable();
    }



}
