package project.presentation.controller.cart;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBase;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.util.Pair;
import project.business.facade.CartFacade;
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
            CartFacade cartFacade = CartFacade.getInstance();
            Map<Meal, Integer> cartMap = new HashMap<>();
            cartMap = (Map<Meal, Integer>) cartFacade.load();

            VBox node = new VBox();
            for (Map.Entry<Meal, Integer> entry : cartMap.entrySet()) {

                Label title = new Label();
                title.setText(entry.getKey().getTitle());

                Label qte = new Label();
                qte.setText(String.valueOf(entry.getValue()));

                HBox cartMeal = new HBox();
                cartMeal.setPadding(new Insets(10, 10, 10, 10));

                Image image = new Image("file:src/main/resources/project/presentation/frame/plus1.png");
                ImageView imageview = new ImageView(image);
                imageview.setFitHeight(30);
                imageview.setPreserveRatio(true);

                Image image2 = new Image("file:src/main/resources/project/presentation/frame/minus.png");
                ImageView imageview2 = new ImageView(image2);
                imageview2.setFitHeight(30);
                imageview2.setPreserveRatio(true);

                Button plusBtn = new Button();
                Button minusBtn = new Button();
                plusBtn.setPrefSize(30, 30);
                minusBtn.setPrefSize(30, 30);

                //Setting a graphic to the button
                plusBtn.setGraphic(imageview);
                minusBtn.setGraphic(imageview2);

                plusBtn.setStyle("-fx-background-color: transparent; -fx-cursor: hand; -fx-alignment: center; -fx-padding: 10px; ");
                minusBtn.setStyle("-fx-background-color: transparent; -fx-cursor: hand; -fx-alignment: center;-fx-padding: 10px; ");
                plusBtn.setId("plusBtn");
                minusBtn.setId("minusBtn");

                Map<Meal, Integer> finalCartMap = cartMap;
                plusBtn.setOnAction((ActionEvent event) -> {
                    int qteValue = Integer.parseInt(qte.getText());
                    qteValue++;
                    qte.setText(String.valueOf(qteValue));
                    finalCartMap.put(entry.getKey(), qteValue);
                    String newCart = "";
                    for(Map.Entry<Meal, Integer> entry2 : finalCartMap.entrySet()) {
                        newCart += "cart_meal=" + entry2.getKey().getIdMeal() + ",qte=" + entry2.getValue() + " ";
                    }
                    try {
                        cartFacade.update(newCart);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });

                Map<Meal, Integer> finalCartMap1 = cartMap;
                minusBtn.setOnAction((ActionEvent event) -> {
                    int qteValue = Integer.parseInt(qte.getText());
                    if (qteValue > 1) {
                        qteValue--;
                        qte.setText(String.valueOf(qteValue));
                        finalCartMap1.put(entry.getKey(), qteValue);
                        String newCart = "";
                        for(Map.Entry<Meal, Integer> entry3 : finalCartMap1.entrySet()) {
                            newCart += "cart_meal=" + entry3.getKey().getIdMeal() + ",qte=" + entry3.getValue() + " ";
                        }
                        try {
                            cartFacade.update(newCart);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                });

                // set the title style
                title.setStyle("-fx-font-size: 15px; -fx-font-weight: bold; -fx-text-fill: #000000; -fx-alignment: center; -fx-padding: 10px;");
                // set the qte style
                qte.setStyle("-fx-font-size: 15px; -fx-font-weight: bold; -fx-text-fill: #000000; -fx-alignment: center; -fx-padding: 10px;");

                cartMeal.getChildren().setAll(title,qte,plusBtn,minusBtn);
                node.getChildren().add(cartMeal);
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
