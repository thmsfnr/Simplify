package project.presentation.controller.cart;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.util.Pair;
import project.business.facade.EventFacade;
import project.business.facade.MealFacade;
import project.business.models.Event;
import project.business.models.Meal;
import project.presentation.controller.event.EventDescriptionController;
import project.presentation.controller.user.PersonalAccountController;
import project.presentation.frame.cart.CartFrame;
import project.presentation.frame.event.EventUserFrame;
import project.presentation.frame.menu.Menu;
import project.utilities.LocalStorage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * Created by Simplify members on 02/01/22.
 * This class is the controller of the CartFrame
 * @author Simplify members
 */
public class CartController implements Initializable {


    // Instance variables


    @FXML
    private AnchorPane contentArea;


    @FXML
    private ScrollPane cartContentArea;

    @FXML
    private AnchorPane MealCartPane;

    @FXML
    private Label mealTitle;

    @FXML
    private Label qteMeal;

    @FXML
    private Button orderBtn;

    @FXML
    private Button clearCartBtn;

    private static int idUser;

    @FXML
    private Button cartBtn;

    @FXML
    private Button deliveryCartBtn;

    @FXML
    private Button back;

    private Boolean isVisible = false;

    /**
     * This method is used to pass the user id to the controller
     * @param idUser the id of the user
     */
    public static void setIdUser(int idUser) {
        CartController.idUser = idUser;
    }


    /**
     * This method is used to initialize the cart frame
     * it will load the reservation cart first and it can be changed to the delivery cart
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // switch to ReservationCartFrame
        this.isVisible = true;


        try {
            String cart = (String) LocalStorage.load("cartStorage");

            //Create a Map data structure to store the cart_meal and qte values
            Map<Meal, Integer> cartMap = new HashMap<>();

            //Split the string on the spaces
            String[] cartItems = cart.split("\\s+");

            VBox node = new VBox();
            for (String cartItem : cartItems) {
                //Split the string on the comma
                String[] cartItemDetails = cartItem.split(",");
                //Get the meal id
                int mealId = Integer.parseInt(cartItemDetails[0].split("=")[1]);
                //Get the meal quantity
                int mealQte = Integer.parseInt(cartItemDetails[1].split("=")[1]);
                //Get the meal object
                MealFacade  mealFacade = MealFacade.getInstance();
                Meal meal = mealFacade.getById(mealId);
                //Add the meal and quantity to the map
                cartMap.put(meal, mealQte);
                Label title = new Label();
                title.setText(meal.getTitle());

                Label qte = new Label();
                qte.setText(String.valueOf(mealQte));
                HBox a = new HBox();
                a.setPadding(new Insets(10, 10, 10, 10));
                a.getChildren().setAll(title,qte);
                node.getChildren().add(a);
                node.setPadding(new Insets(10));
            }
            cartContentArea.setContent(node);
            // Activate the scroll bar
            cartContentArea.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * This method is used to show the cart frame
     * On click on the Cart button
     */
    @FXML
    public void showCartContent() {
        if (isVisible) {
            contentArea.setVisible(false);
            isVisible = false;
        } else {
            contentArea.setVisible(true);
            isVisible = true;
        }
    }

    /**
     * This method is used to manage the event of the back button
     * @param event the event of the back button
     */
    public void backToMenu(ActionEvent event) throws Exception {
        Window owner = back.getScene().getWindow();
        Menu menu = new Menu();
        menu.start(new Stage());
        owner.hide();
    }

    public void order(){

    }

    public void clearCart(ActionEvent event){
        // clear the scroll pane cart content pane
        cartContentArea.setContent(null);
        // clear the local storage
        try {
            LocalStorage.write("cartStorage", "");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
