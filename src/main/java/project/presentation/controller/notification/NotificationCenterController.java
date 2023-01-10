
package project.presentation.controller.notification;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.util.Callback;
import project.business.facade.NotificationFacade;
import project.business.models.Notification;
import project.presentation.frame.menu.Menu;
import project.utilities.Display;
import project.utilities.LocalStorage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class NotificationCenterController implements Initializable {

    private static int user;
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
    @FXML
    private Button back;

    /**
     * This method is used to pass the user id to the controller
     * @param idUser the id of the user
     */
    public static void setIdUser(int idUser) {
        NotificationCenterController.user = idUser;
    }

    /**
     * This method is used to initialize the controller of Notification
     * @param url the url
     * @param resourceBundle the resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        NotificationFacade notificationFacade = NotificationFacade.getInstance();
        ObservableList<Notification> notifications;

        notifications = FXCollections.observableList(notificationFacade.getAllNotifications(user));

        idNotification.setCellValueFactory(new PropertyValueFactory<>("idNotification"));
        title.setCellValueFactory(new PropertyValueFactory<>("title"));
        description.setCellValueFactory(new PropertyValueFactory<>("description"));
        idUser.setCellValueFactory(new PropertyValueFactory<>("idUser"));
        TableNotification.setItems(notifications);
        addDeleteButtonToTable();
    }

    /**
     * This method is used to add the delete button to the table
     */
    private void addDeleteButtonToTable() {
        // Create a new column
        TableColumn<Notification, Void> colBtn = new TableColumn("Delete");

        // Add the button to the column
        Callback<TableColumn<Notification, Void>, TableCell<Notification, Void>> cellFactory = new Callback<>() {
            @Override
            public TableCell<Notification, Void> call(final TableColumn<Notification, Void> param) {
                final TableCell<Notification, Void> cell = new TableCell<>() {

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
                                deleteNotification(event, data);
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

    /**
     * This method is use to delete a notification
     * @param event the event of the button
     * @param notification the notification to delete
     */
    public void deleteNotification(ActionEvent event, Notification notification) {

        // Create a new alert to confirm the deletion
        Window owner = TableNotification.getScene().getWindow();

        // Call the facade to delete the table from the database
        NotificationFacade notificationFacade = NotificationFacade.getInstance();
        Boolean result = notificationFacade.deleteNotification(notification.getIdNotification());
        if (result) {
            Display.infoBox("Notification deleted successfully!", null, "Success");
        } else {
            Display.showAlert(Alert.AlertType.ERROR, owner, "Form Error!",
                    "Please enter your name");
            return;
        }
        // Refresh the table
        refreshTable();
    }

    /**
     * This method is used to refresh the table
     */
    private void refreshTable() {
        // Call the facade to get
        NotificationFacade notificationFacade = NotificationFacade.getInstance();
        ObservableList<Notification> notifications = null;
        notifications = FXCollections.observableList(notificationFacade.getAllNotifications(user));
        // Set the table
        TableNotification.setItems(notifications);
    }

    /**
     * This method is used to manage the event of the back button
     * @param event the event of the back button
     */
    public void backToMenu(ActionEvent event) throws Exception {
        Window owner = back.getScene().getWindow();
        project.presentation.frame.menu.Menu menu = new Menu();
        menu.start(new Stage());
        owner.hide();
    }

}
