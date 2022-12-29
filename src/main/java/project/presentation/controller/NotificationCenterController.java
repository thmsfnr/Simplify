package project.presentation.controller;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Window;
import javafx.util.Callback;
import project.business.facade.NotificationFacade;
import project.business.models.Notification;
import project.utilities.LocalStorage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static project.presentation.controller.Display.infoBox;
import static project.presentation.controller.Display.showAlert;

public class NotificationCenterController implements Initializable {
    @FXML
    private TableView<Notification> TableNotification;
    @FXML
    private TableColumn<Notification, Integer> idNotification;
    @FXML
    private TableColumn<Notification, String> title;

    @FXML
    private TableColumn<Notification, String> description;

    @FXML
    private TableColumn<Notification, Integer> idUser;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        NotificationFacade notificationFacade = NotificationFacade.getInstance();
        ObservableList<Notification> notifications;
        try {
            notifications = notificationFacade.getAllNotifications((Integer) LocalStorage.load("user_id"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        idNotification.setCellValueFactory(new PropertyValueFactory<Notification, Integer>("idNotification"));
        title.setCellValueFactory(new PropertyValueFactory<Notification, String>("title"));
        description.setCellValueFactory(new PropertyValueFactory<Notification, String>("description"));
        idUser.setCellValueFactory(new PropertyValueFactory<Notification, Integer>("idUser"));
        TableNotification.setItems(notifications);
        addDeleteButtonToTable();
    }

    private void addDeleteButtonToTable() {
        // Create a new column
        TableColumn<Notification, Void> colBtn = new TableColumn("Delete");

        // Add the button to the column
        Callback<TableColumn<Notification, Void>, TableCell<Notification, Void>> cellFactory = new Callback<TableColumn<Notification, Void>, TableCell<Notification, Void>>() {
            @Override
            public TableCell<Notification, Void> call(final TableColumn<Notification, Void> param) {
                final TableCell<Notification, Void> cell = new TableCell<Notification, Void>() {

                    private final Button btn = new Button();
                    {
                        btn.setOnAction((ActionEvent event) -> {
                            Notification data = getTableView().getItems().get(getIndex());
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
                                Notification data = getTableView().getItems().get(getIndex());
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
        TableNotification.getColumns().add(colBtn);
    }
    public void deleteTable(ActionEvent event, Notification data) {

        // Create a new alert to confirm the deletion
        Window owner = TableNotification.getScene().getWindow();

        // Call the facade to delete the table from the database
        NotificationFacade notificationFacade = NotificationFacade.getInstance();
        Boolean result = notificationFacade.deleteNotification(data.getIdNotification());
        if (result) {
            infoBox("Notification deleted successfully!", null, "Success");
        } else {
            showAlert(Alert.AlertType.ERROR, owner, "Form Error!",
                    "Please enter your name");
            return;
        }
        // Refresh the table
       // refreshTable();
    }

    public void refreshTable() {
        // Call the facade to get
        NotificationFacade notificationFacade = NotificationFacade.getInstance();
        ObservableList<Notification> notifications = null;
        try {
            notifications = notificationFacade.getAllNotifications((Integer) LocalStorage.load("user_id"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        // Set the table
        TableNotification.setItems(notifications);
    }

}
