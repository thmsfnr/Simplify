package project.business.facade;


import javafx.scene.layout.VBox;
import project.business.models.Meal;
import project.utilities.CartStorage;
import project.utilities.LocalStorage;

import java.io.IOException;
import java.util.Map;

public class CartFacade {
    private CartFacade() {}

    public void update(String cart) throws IOException {
        CartStorage.write("cartStorage", cart);
    }

    /**
     * This method is used to get the meals and theirs quatities stored in the file to pop them in ther cart
     * @return a list of the meals and their quantities
     * @throws IOException if the file is not found
     */
    public Object load() throws IOException { return CartStorage.load("cartStorage");}

    public static CartFacade getInstance() {
        return CartFacade.FacadeHolder.INSTANCE;
    }

    /**
     * This class is used to get the instance of the class
     * it's a holder class for the singleton
     */
    private static class FacadeHolder {
        // Instance of the class UserFacade
        static final CartFacade INSTANCE = new CartFacade();
    }
}
