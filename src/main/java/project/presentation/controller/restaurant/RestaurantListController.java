package project.presentation.controller.restaurant;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.stage.Window;
import project.business.facade.RestaurantFacade;
import project.business.models.Restaurant;
import project.utilities.Display;

import javafx.util.Callback;
import project.exceptions.AccessDatabaseException;
import project.exceptions.RestaurantNotFoundException;
import project.presentation.frame.reservation.RestaurantForm;
import project.presentation.frame.restaurant.RestaurantInfos;

import java.io.IOException;

public class RestaurantListController {

    private boolean manage = false;
    private int idManager;

    @FXML
    private TableView<Restaurant> restaurantTable;

    @FXML
    private TableColumn<Restaurant, Integer> idColumn;

    @FXML
    private TableColumn<Restaurant, String> nameColumn;

    @FXML
    private TableColumn<Restaurant, Integer> responsableColumn;

    @FXML
    private Button addButton;


    private void addUpdateButton(){
        TableColumn<Restaurant, Void> colBtn = new TableColumn("Update");

        Callback<TableColumn<Restaurant, Void>, TableCell<Restaurant, Void>> cellFactory = new Callback<TableColumn<Restaurant, Void>, TableCell<Restaurant, Void>>() {
            @Override
            public TableCell<Restaurant, Void> call(final TableColumn<Restaurant, Void> param) {
                final TableCell<Restaurant, Void> cell = new TableCell<Restaurant, Void>() {

                    private final javafx.scene.control.Button btn = new javafx.scene.control.Button();

                    {
                        btn.setOnAction((javafx.event.ActionEvent event) -> {
                            try{
                                Restaurant data = getTableView().getItems().get(getIndex());
                                Window owner = btn.getScene().getWindow();
                                FXMLLoader loader = new FXMLLoader(RestaurantForm.class.getResource("RestaurantFormFrame.fxml"));
                                Parent root = loader.load();
                                RestaurantFormController controller = loader.getController();
                                controller.setFields(data);
                                Stage stage = new Stage();
                                stage.setTitle("Update restaurant");
                                stage.setScene(new javafx.scene.Scene(root));
                                stage.show();
                                owner.hide();

                            } catch (IOException e) {
                                e.printStackTrace();
                            }


                        });
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            Image image = new Image("file:src/main/resources/project/presentation/frame/update.png");
                            ImageView imageView = new ImageView(image);
                            imageView.setFitHeight(20);
                            imageView.setPreserveRatio(true);

                            btn.setPrefSize(20,20);
                            btn.setGraphic(imageView);
                            btn.setStyle("-fx-background-color: transparent; -fx-cursor: hand; -fx-alignment: center; -fx-border-color: #c8ded0; -fx-border-width: 1px; -fx-border-radius: 5px;");
                            setGraphic(btn);
                        }
                    }
                };
                return cell;
            }
        };

        colBtn.setCellFactory(cellFactory);
        restaurantTable.getColumns().add(colBtn);
    }


    public void addButtonDelete(){
        TableColumn<Restaurant, Void> colBtn = new TableColumn("Delete");

        Callback<TableColumn<Restaurant, Void>, TableCell<Restaurant, Void>> cellFactory = new Callback<TableColumn<Restaurant, Void>, TableCell<Restaurant, Void>>() {
            @Override
            public TableCell<Restaurant, Void> call(final TableColumn<Restaurant, Void> param) {
                final TableCell<Restaurant, Void> cell = new TableCell<Restaurant, Void>() {

                    private final javafx.scene.control.Button btn = new javafx.scene.control.Button();

                    {
                        btn.setOnAction((javafx.event.ActionEvent event) -> {
                            Restaurant data = getTableView().getItems().get(getIndex());
                            try {
                                RestaurantFacade restaurantFacade = RestaurantFacade.getInstance();
                                restaurantFacade.deleteRestauranteById(data.getIdRestaurant());
                            } catch (AccessDatabaseException e) {
                                e.printStackTrace();
                                Display.showAlert(Alert.AlertType.ERROR, btn.getScene().getWindow(), "Error", "Error deleting restaurant");
                            }
                            refreshTable();
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
                            btn.setId("delete");
                            setGraphic(btn);
                        }
                    }
                };
                return cell;
            }
        };

        colBtn.setCellFactory(cellFactory);
        restaurantTable.getColumns().add(colBtn);
    }



    public void addDetailsButton(){
        System.out.println("addDetailsButton");
        TableColumn<Restaurant, Void> colBtn = new TableColumn("Details");

        Callback<TableColumn<Restaurant, Void>, TableCell<Restaurant, Void>> cellFactory = new Callback<TableColumn<Restaurant, Void>, TableCell<Restaurant, Void>>() {
            @Override
            public TableCell<Restaurant, Void> call(final TableColumn<Restaurant, Void> param) {
                final TableCell<Restaurant, Void> cell = new TableCell<Restaurant, Void>() {

                    private final javafx.scene.control.Button btn = new javafx.scene.control.Button();

                    {
                        btn.setOnAction((javafx.event.ActionEvent event) -> {
                            Restaurant data = getTableView().getItems().get(getIndex());
                            try {
                                RestaurantFacade restaurantFacade = RestaurantFacade.getInstance();
                                Restaurant restaurant = restaurantFacade.getRestaurantById(data.getIdRestaurant());
                                Window owner = btn.getScene().getWindow();
                                FXMLLoader loader = new FXMLLoader(RestaurantInfos.class.getResource("RestaurantInfosFrame.fxml"));
                                Parent root = loader.load();
                                RestaurantInfosController controller = loader.getController();
                                controller.initialize(restaurant);
                                Stage stage = new Stage();
                                stage.setTitle("Restaurant details");
                                stage.setScene(new javafx.scene.Scene(root));
                                stage.show();
                                owner.hide();

                            } catch (Exception e) {
                                e.printStackTrace();
                                Display.showAlert(Alert.AlertType.ERROR, btn.getScene().getWindow(), "Error", "Error getting restaurant");
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
                            btn.setId("details");
                            setGraphic(btn);
                        }
                    }
                };
                return cell;
            }
        };

        colBtn.setCellFactory(cellFactory);
        restaurantTable.getColumns().add(colBtn);
    }



    public void refreshTable(){
        RestaurantFacade restaurantFacade = RestaurantFacade.getInstance();
        try {
            ObservableList<Restaurant> restaurants = FXCollections.observableList(restaurantFacade.getAllRestaurants());
            restaurantTable.setItems(restaurants);
        } catch (AccessDatabaseException e) {
            e.printStackTrace();
        }
        catch (RestaurantNotFoundException e){
            e.printStackTrace();
        }
        catch(Exception e){
            Display.showAlert(Alert.AlertType.ERROR, restaurantTable.getScene().getWindow(), "Error", "Error loading restaurants");
        }
    }


    @FXML
    public void addRestaurant(ActionEvent event){
        try{
            Window owner = addButton.getScene().getWindow();
            RestaurantForm restaurantForm = new RestaurantForm();
            restaurantForm.start(new Stage());
            owner.hide();
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }




    public void initialize(){
        RestaurantFacade restaurantFacade = RestaurantFacade.getInstance();

        try{
            ObservableList<Restaurant> restaurants = FXCollections.observableList(restaurantFacade.getAllRestaurants());
            idColumn.setCellValueFactory(new PropertyValueFactory<Restaurant, Integer>("idRestaurant"));
            nameColumn.setCellValueFactory(new PropertyValueFactory<Restaurant, String>("name"));
            responsableColumn.setCellValueFactory(new PropertyValueFactory<Restaurant, Integer>("idManager"));
            restaurantTable.setItems(restaurants);
        }
        catch(AccessDatabaseException e){
            Display.showAlert(Alert.AlertType.ERROR, null, "Error", "An error occurred in the back, please retry later.");
        } catch (RestaurantNotFoundException e) {
            Display.showAlert(Alert.AlertType.ERROR, null, "Error", "An error occurred in the back, please retry later.");
        }

        addUpdateButton();
        addButtonDelete();
        addDetailsButton();
    }

}
