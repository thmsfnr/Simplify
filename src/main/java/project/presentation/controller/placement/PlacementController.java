package project.presentation.controller.placement;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Stage;
import javafx.stage.Window;
import project.business.facade.PlacementFacade;
import project.business.models.Meal;
import project.business.models.Restaurant;
import project.business.models.Table;
import project.exceptions.AccessDatabaseException;
import project.exceptions.RestaurantNotFoundException;
import project.presentation.controller.reservation.ReservationFormController;
import project.utilities.Display;
import project.utilities.LocalStorage;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class PlacementController {

    private static Boolean isReservation;

    private static int idRestaurant;

    private ReservationFormController reservationFormController;

    private HashMap<Position, Integer> positions = new HashMap<>();

    @FXML
    private SplitPane page;

    @FXML
    private AnchorPane leftPane;

    @FXML
    private AnchorPane rightPane;

    @FXML
    private ListView<Table> listView;

    @FXML
    private Spinner<Integer> xSpinner;

    @FXML
    private Spinner<Integer> ySpinner;

    @FXML
    private Button updateButton;

    @FXML
    private Button removeButton;

    @FXML
    private Button switchButton;

    @FXML
    private GridPane gridPane;

    @FXML
    private Label label_position;

    @FXML
    private Label label_xSpinner;

    @FXML
    private Label label_ySpinner;

    @FXML
    private AnchorPane anchorPlacement;

    @FXML
    private Button button_reserve_table;

    public static void setIsReservation(Boolean isReservation) {
        PlacementController.isReservation = isReservation;
    }
    public static void setIdRestaurant(int idRestaurant) {
        PlacementController.idRestaurant = idRestaurant;
    }

    @FXML
    private void initialize(){
        try{
            //if the frame is opened from the reservation frame
            if(isReservation){
                button_reserve_table.setVisible(true);
                button_reserve_table.setDisable(false);
                configurationForReservation();
            }else{
                button_reserve_table.setVisible(false);
                button_reserve_table.setDisable(true);
            }
            //Recuperation of the restaurant
            PlacementFacade placementFacade = PlacementFacade.getInstance();
            Restaurant restaurant = placementFacade.getRestaurantById(idRestaurant);

            //Creation of the grid
            gridPane = new GridPane();
            gridPane.setGridLinesVisible(true);
            gridPane.setStyle("-fx-background-color: #FFFFFF;");
            //gridPane.setPrefSize(400, 400);

            int columns = restaurant.getWidth();
            int rows = restaurant.getLength();
            for(int i = 0; i<columns; i++){
                ColumnConstraints colConst = new ColumnConstraints();
                colConst.setPrefWidth(55.0);
                //colConst.setPercentWidth(100.0/columns);
                gridPane.getColumnConstraints().add(colConst);
            }

            for(int i =0; i<rows; i++){
                RowConstraints rowConst = new RowConstraints();
                rowConst.setPrefHeight(45.0);
                //rowConst.setPercentHeight(100.0/rows);
                gridPane.getRowConstraints().add(rowConst);
            }


            //Creation of the hashmap that contains the position of the tables
            for(int i = 0; i<columns; i++){
                for(int j = 0; j<rows; j++){
                    positions.put(new Position(i,j), -1);
                }
            }

            AddTables(placementFacade, idRestaurant);


            leftPane.getChildren().add(gridPane);


            //Right Pane
            listView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

            SpinnerValueFactory<Integer> xValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, columns-1, 0);
            SpinnerValueFactory<Integer> yValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, rows-1, 0);
            xSpinner.setValueFactory(xValueFactory);
            ySpinner.setValueFactory(yValueFactory);
            xSpinner.setEditable(true);
            ySpinner.setEditable(true);
        }
        catch(AccessDatabaseException e) {
            project.utilities.Display.showAlert(Alert.AlertType.ERROR, null, "Error", "Error during the loading, retry later please");
        }
        catch(RestaurantNotFoundException e){
            Display.showAlert(Alert.AlertType.ERROR, null, "Error", "Restaurant not found");
        }
        // add a listener to the buttuon reserve_table in placementController to get the table selected
        getButton_reserve_table().setOnAction(actionEvent -> {
           selection_table();
        });
    }


    @FXML
    public void addReservation(){

    }


    private void AddTables(PlacementFacade placementFacade, int idRestaurant) throws AccessDatabaseException, RestaurantNotFoundException {
        List<Table> tables = placementFacade.getAllTablesOfRestaurant(idRestaurant);

        for(Table table : tables){
            int x = table.getX();
            int y = table.getY();
            if(x > -1 && y > -1){
                positions.remove(new Position(x,y));
                positions.put(new Position(x,y), table.getIdTable());
            }
        }

        for(Position key : positions.keySet()){
            if(positions.get(key) != -1){
                LabelHashable label = new LabelHashable("Table " + positions.get(key));
                label.setStyle("-fx-background-color: #80ff80;");
                gridPane.add(label, key.getX(), key.getY());
            }
        }

        listView.getItems().addAll(tables);
    }


    @FXML
    public synchronized void deletePlacement(ActionEvent event){
        ObservableList<Table> tableSelected = listView.getSelectionModel().getSelectedItems();
        Table table = tableSelected.get(0);

        if(tableSelected.size() == 0){
            Display.showAlert(Alert.AlertType.ERROR, null, "Error", "You must select a table");
        }
        else{
            int idTable = table.getIdTable();
            if(positions.get(new Position(table.getX(), table.getY())) == idTable){
                PlacementFacade placementFacade = PlacementFacade.getInstance();
                try{
                    placementFacade.deletePlacement(idTable);
                    positions.put(new Position(table.getX(), table.getY()), -1);
                    gridPane.getChildren().remove(new LabelHashable("Table " + idTable));
                }
                catch(AccessDatabaseException e){
                    Display.showAlert(Alert.AlertType.ERROR, null, "Error", "An error occured, retry later please");
                }
            }
            else{
                Display.showAlert(Alert.AlertType.ERROR, null, "Error", "The table is not placed");
            }
        }

    }


    @FXML
    public synchronized void updatePlacement(ActionEvent event){
        ObservableList<Table> tableSelected = listView.getSelectionModel().getSelectedItems();

        if(tableSelected.size() == 0){
            Display.showAlert(Alert.AlertType.ERROR, null, "Error", "You must select a table");
        }
        else {
            Table table = tableSelected.get(0);
            int idTable = table.getIdTable();
            int x = xSpinner.getValue();
            int y = ySpinner.getValue();

            if (positions.get(new Position(x, y)) == -1) {
                PlacementFacade placementFacade = PlacementFacade.getInstance();
                try {
                    placementFacade.updatePlacement(idTable, x, y);
                    positions.put(new Position(table.getX(), table.getY()), -1);
                    positions.put(new Position(x, y), idTable);
                    gridPane.getChildren().remove(new LabelHashable("Table " + idTable));
                    table.setX(x);
                    table.setY(y);
                    LabelHashable label = new LabelHashable("Table " + idTable);
                    label.setStyle("-fx-background-color: #80ff80;");
                    gridPane.add(label, x, y);
                } catch (AccessDatabaseException e) {
                    Display.showAlert(Alert.AlertType.ERROR, null, "Error", "An error occured, retry later please");
                }
            } else {
                Display.showAlert(Alert.AlertType.ERROR, null, "Error", "This position is already occupied");
            }
        }
    }


    @FXML
    public void SwitchToManagementTablePage(ActionEvent event){
        Window owner = switchButton.getScene().getWindow();
        project.presentation.frame.table.Table tableManagement = new project.presentation.frame.table.Table();
        try {
            tableManagement.start(new Stage());
            owner.hide();
        } catch (Exception e) {
            Display.showAlert(Alert.AlertType.ERROR, owner, "Error", "An error occured, retry later please");
        }
    }


    public void configurationForReservation(){
        leftPane.setMaxWidth(885);
        leftPane.setMaxHeight(352);
        rightPane.setMaxHeight(352);
        rightPane.setMaxWidth(885);
        anchorPlacement.setMinWidth(885);
        anchorPlacement.setMinHeight(352);
        label_position.setVisible(false);
        label_xSpinner.setVisible(false);
        label_ySpinner.setVisible(false);
        xSpinner.setVisible(false);
        xSpinner.setDisable(true);
        ySpinner.setDisable(true);
        ySpinner.setVisible(false);
        updateButton.setDisable(true);
        removeButton.setDisable(true);
        removeButton.setVisible(false);
        updateButton.setVisible(false);
        switchButton.setDisable(true);
        switchButton.setVisible(false);
    }

    public Button getButton_reserve_table() {
        return button_reserve_table;
    }
    public ListView<Table> getListView() {
        return listView;
    }
    private void selection_table() {
        Table tableSelected = listView.getSelectionModel().getSelectedItem();
        //send tableSelected to the ReservationFormController
        reservationFormController.selection_table(tableSelected);
    }
    public void setReservationFormController(ReservationFormController reservationFormController) {
        this.reservationFormController = reservationFormController;
    }


    private class Position {
        private int x;
        private int y;

        public Position(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        public void setX(int x) {
            this.x = x;
        }

        public void setY(int y) {
            this.y = y;
        }

        @Override
        public String toString() {
            return "Position [x=" + x + ", y=" + y + "]";
        }

        @Override
        public boolean equals(Object o){
            if(o == this){
                return true;
            }
            if(!(o instanceof Position)){
                return false;
            }
            Position position = (Position) o;
            return position.getX() == this.getX() && position.getY() == this.getY();
        }

        @Override
        public int hashCode(){
            return Objects.hash(x, y);
        }
    }


    private class LabelHashable extends Label{

        public LabelHashable(String text){
            super(text);
        }
        @Override
        public boolean equals(Object o){
            if(o == this){
                return true;
            }
            if(!(o instanceof Label)){
                return false;
            }
            Label label = (Label) o;
            return label.getText().equals(this.getText());
        }

        @Override
        public int hashCode(){
            return Objects.hash(this.getText());
        }
    }

}
