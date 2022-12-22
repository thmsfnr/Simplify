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
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.Window;
import project.business.facade.TableFacade;
import project.business.models.Table;

import javafx.event.ActionEvent;

import javafx.util.Callback;


import static project.presentation.controller.Display.infoBox;
import static project.presentation.controller.Display.showAlert;

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





    private void addUpdateButtonToTable() {
        TableColumn<Table, Void> colBtn = new TableColumn("Update");

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
                            Image image = new Image("file:src/main/resources/project/presentation/frame/update.png");
                            ImageView imageview = new ImageView(image);
                            imageview.setFitHeight(20);
                            imageview.setPreserveRatio(true);

                            btn.setPrefSize(50, 50);
                            //Setting a graphic to the button
                            btn.setGraphic(imageview);
                            btn.setStyle("-fx-background-color: transparent; -fx-cursor: hand; -fx-alignment: center; -fx-border-color: #c8ded0; -fx-border-width: 1px; -fx-border-radius: 5px;");
                            btn.setId("updateBtn");

                            // on action ouvre la fenetre detailsTable
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

        colBtn.setCellFactory(cellFactory);

        TableTab.getColumns().add(colBtn);

    }

    public void updateTable(ActionEvent event, Table data)
    {
        /*
        Label secondLabel = new Label("Hello World");
        StackPane secondaryLayout = new StackPane();
        secondaryLayout.getChildren().add(secondLabel);

        Scene secondScene = new Scene(secondaryLayout, 230, 100);

        // New window (Stage)
        Stage newWindow = new Stage();
        newWindow.setTitle("Second Stage");
        newWindow.setScene(secondScene);

        // Set position of second window, related to primary window.
        newWindow.show();

         */
        try{
            FXMLLoader loader = new FXMLLoader(project.presentation.frame.Table.class.getResource("TableDetails.fxml"));
            Parent root = loader.load();
            DetailsTableController controller = loader.getController();
            controller.setTextFields(data);
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


    private void addDeleteButtonToTable() {

        TableColumn<Table, Void> colBtn = new TableColumn("Delete");
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

        colBtn.setCellFactory(cellFactory);

        TableTab.getColumns().add(colBtn);

    }

    public void deleteTable(ActionEvent event, Table data) {

        Window owner = TableTab.getScene().getWindow();
        // Call the facade to get
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

    public void refreshTable() {
        // Call the facade to get
        TableFacade tableFacade = TableFacade.getInstance();
        ObservableList<Table> tables = tableFacade.getAllTables();
        // Set the table
        TableTab.setItems(tables);
    }

    public void initialize(java.net.URL location, java.util.ResourceBundle resources) {

        TableFacade tableFacade = TableFacade.getInstance();

        // Create an array list of all the tables
        ObservableList<Table> tables = tableFacade.getAllTables();

        idTable.setCellValueFactory(new PropertyValueFactory<Table, Integer>("idTable"));
        name.setCellValueFactory(new PropertyValueFactory<Table, String>("name"));
        description.setCellValueFactory(new PropertyValueFactory<Table, String>("description"));
        booked.setCellValueFactory(new PropertyValueFactory<Table, String>("booked"));
        TableTab.setItems(tables);

        addUpdateButtonToTable();
        addDeleteButtonToTable();
    }



}
