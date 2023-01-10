package project.presentation.controller.delivery;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.util.Callback;
import project.business.facade.DeliveryFacade;
import project.business.facade.RestaurantFacade;
import project.business.facade.UserFacade;
import project.business.models.Delivery;
import project.business.models.Restaurant;
import project.business.models.State;
import project.business.models.User;
import project.exceptions.AccessDatabaseException;
import project.presentation.controller.menu.MenuController;
import project.presentation.frame.delivery.DeliveryInfo;
import project.utilities.Display;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Simplify members on 07/01/23.
 * This class is the controller of the delivery list frame
 * @author Simplify members
 */
public class DeliveryListController {
    private static Integer idUser;

    private static Integer idRole;

    @FXML
    private TableView<Delivery> deliveryPassedTable;


    @FXML
    private TableView<Delivery> deliveryNewsTable;

    @FXML
    private TableColumn<Delivery, Integer> idColumn2;

    @FXML
    private TableColumn<Delivery, Integer> idRestaurantColumn2;

    @FXML
    private TableColumn<Delivery, State> stateColumn2;

    @FXML
    private TableColumn<Delivery, Integer> idColumn;

    @FXML
    private TableColumn<Delivery, Integer> idRestaurantColumn;

    @FXML
    private TableColumn<Delivery, State> stateColumn;

    @FXML
    private Button backButton;


    /**
     * This method allows to set the id of the user which will see the frame
     * @param idUser_new the id of the user
     */
    public static void setUserId(Integer idUser_new) {
        idUser = idUser_new;
    }

    public static Integer getUserId() {
        return idUser;
    }

    @FXML
    public void initialize() {
        UserFacade userFacade = UserFacade.getInstance();
        User user = userFacade.getById(idUser);
        idRole = user.getRole();

        idColumn.setCellValueFactory(new PropertyValueFactory<Delivery, Integer>("idDelivery"));
        idRestaurantColumn.setCellValueFactory(new PropertyValueFactory<Delivery, Integer>("idRestaurant"));
        stateColumn.setCellValueFactory(new PropertyValueFactory<Delivery, State>("state"));
        idColumn2.setCellValueFactory(new PropertyValueFactory<Delivery, Integer>("idDelivery"));
        idRestaurantColumn2.setCellValueFactory(new PropertyValueFactory<Delivery, Integer>("idRestaurant"));
        stateColumn2.setCellValueFactory(new PropertyValueFactory<Delivery, State>("state"));

        refreshPassedTable();

        addDeleteButton();
        addDetailsButton();
    }


    /**
     * This method allows to refresh the tables
     */
    public void refreshPassedTable(){
        DeliveryFacade deliveryFacade = DeliveryFacade.getInstance();

        try{
            List<Delivery> list_deliveries = new ArrayList<>();
            if(idRole == 2){
                RestaurantFacade restaurantFacade = RestaurantFacade.getInstance();
                List<Restaurant> restaurants = restaurantFacade.getRestaurantsOfUser(idUser);
                list_deliveries = deliveryFacade.getAllDeliveriesOfRestaurant(restaurants.get(0).getIdRestaurant());
            }else{
                list_deliveries = deliveryFacade.getAllDeliveriesOfUser(idUser);
            }

            List<Delivery> old_deliveries = new ArrayList<>();
            List<Delivery> new_deliveries = new ArrayList<>();

            for(Delivery delivery : list_deliveries){
                if(delivery.getState() == State.DELIVERED){
                    old_deliveries.add(delivery);
                }else{
                    new_deliveries.add(delivery);
                }
            }

            ObservableList<Delivery> old_deliveries_obs = FXCollections.observableArrayList(old_deliveries);
            ObservableList<Delivery> new_deliveries_obs = FXCollections.observableArrayList(new_deliveries);
            deliveryNewsTable.setItems(new_deliveries_obs);
            deliveryPassedTable.setItems(old_deliveries_obs);
        }
        catch (AccessDatabaseException e) {
            Display.showAlert(Alert.AlertType.ERROR, null, "Error", "Error while loading deliveries");
        }
    }



    /**
     * This method allows to add a button to delete a delivery
     */
    private void addDeleteButton(){
        TableColumn<Delivery, Void> colBtn = new TableColumn("Delete");

        Callback<TableColumn<Delivery, Void>, TableCell<Delivery, Void>> cellFactory = new Callback<TableColumn<Delivery, Void>, TableCell<Delivery, Void>>() {
            @Override
            public TableCell<Delivery, Void> call(final TableColumn<Delivery, Void> param) {
                final TableCell<Delivery, Void> cell = new TableCell<Delivery, Void>() {

                    private final Button btn = new Button("Delete");

                    {
                        btn.setOnAction((ActionEvent event) -> {
                            Delivery data = getTableView().getItems().get(getIndex());
                            try {
                                DeliveryFacade.getInstance().deleteDelivery(data.getIdDelivery());
                            } catch (AccessDatabaseException e) {
                                Display.showAlert(Alert.AlertType.ERROR, null, "Error", "Error while deleting delivery");
                            }
                            refreshPassedTable();
                        });
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            Image image = new Image("file:src/main/resources/project/presentation/frame/delete.png");
                            ImageView imageView = new ImageView(image);
                            imageView.setFitHeight(20);
                            imageView.setPreserveRatio(true);

                            btn.setPrefSize(20, 20);
                            btn.setGraphic(imageView);
                            btn.setStyle("-fx-background-color: transparent; -fx-cursor: hand; -fx-alignment: center; -fx-border-color: #c8ded0; -fx-border-width: 1px; -fx-border-radius: 5px;");
                            btn.setId("deleteButton");
                            setGraphic(btn);
                        }
                    }
                };
                return cell;
            }
        };

        colBtn.setCellFactory(cellFactory);
        deliveryPassedTable.getColumns().add(colBtn);
    }


    /**
     * This method allows to add a button to see the details of a delivery and open DeliveryInfoFrame
     */
    private void addDetailsButton(){
        TableColumn<Delivery, Void> colBtn = new TableColumn("Details");

        Callback<TableColumn<Delivery, Void>, TableCell<Delivery, Void>> cellFactory = new Callback<TableColumn<Delivery, Void>, TableCell<Delivery, Void>>() {
            @Override
            public TableCell<Delivery, Void> call(final TableColumn<Delivery, Void> param) {
                final TableCell<Delivery, Void> cell = new TableCell<Delivery, Void>() {

                    private final Button btn = new Button("Details");

                    {
                        btn.setOnAction((ActionEvent event) -> {
                            try{
                                Delivery data = getTableView().getItems().get(getIndex());
                                Window owner = btn.getScene().getWindow();
                                FXMLLoader loader = new FXMLLoader(DeliveryInfo.class.getResource("DeliveryInfoFrame.fxml"));
                                Parent root = loader.load();
                                DeliveryInfoController controller = loader.getController();

                                controller.initialize(data, idRole, null);

                                Stage stage = new Stage();
                                stage.setTitle("Delivery Info");
                                stage.setScene(new Scene(root));
                                stage.show();
                                owner.hide();

                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        });
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            Image image = new Image("file:src/main/resources/project/presentation/frame/details.png");
                            ImageView imageView = new ImageView(image);
                            imageView.setFitHeight(20);
                            imageView.setPreserveRatio(true);

                            btn.setPrefSize(20, 20);
                            btn.setGraphic(imageView);
                            btn.setStyle("-fx-background-color: transparent; -fx-cursor: hand; -fx-alignment: center; -fx-border-color: #c8ded0; -fx-border-width: 1px; -fx-border-radius: 5px;");
                            btn.setId("detailsButton");
                            setGraphic(btn);
                        }
                    }
                };
                return cell;
            }
        };

        colBtn.setCellFactory(cellFactory);
        deliveryPassedTable.getColumns().add(colBtn);
        deliveryNewsTable.getColumns().add(colBtn);
    }


    @FXML
    public void backToMenu(ActionEvent actionEvent){
        try{
            Window owner = backButton.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(Menu.class.getResource("MenuFrame.fxml"));
            Parent root = loader.load();
            MenuController controller = loader.getController();
            controller.initialize();
            Stage stage = new Stage();
            stage.setTitle("Menu");
            stage.setScene(new Scene(root));
            stage.show();
            owner.hide();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }



}
