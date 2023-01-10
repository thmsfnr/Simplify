
package project.business.facade;

import project.utilities.CartStorage;
import java.io.IOException;

/**
 * Created by Simplify members on 10/01/23.
 * This class is the facade of the cart
 * @author Simplify members
 */
public class CartFacade {

    /**
     * This method is used to create a cart facade
     */
    private CartFacade() {}

    /**
     * This method is used to save the cart in the local storage
     * @param cart the cart to save
     * @throws IOException if the file is not found
     */
    public void update(String cart) throws IOException {
        CartStorage.write("cartStorage", cart);
    }

    /**
     * This method is used to get the meals and theirs quatities stored in the file to pop them in ther cart
     * @return a list of the meals and their quantities
     * @throws IOException if the file is not found
     */
    public Object load() throws IOException {
        return CartStorage.load("cartStorage");
    }

    /**
     * This method is used to get the instance of the cart facade
     * @return a cart facade
     */
    public static CartFacade getInstance() {
        return CartFacade.FacadeHolder.INSTANCE;
    }

    /**
     * This class is used to get the instance of the class
     * it's a holder class for the singleton
     */
    private static class FacadeHolder {
        static final CartFacade INSTANCE = new CartFacade();
    }

}
