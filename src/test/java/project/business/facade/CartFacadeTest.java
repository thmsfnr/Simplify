
package project.business.facade;

import org.junit.jupiter.api.Test;
import project.utilities.CartStorage;
import java.io.IOException;
import static org.junit.jupiter.api.Assertions.*;

public class CartFacadeTest {

    @Test
    public void testUpdate() throws IOException {
        CartFacade facade = CartFacade.getInstance();
        String cart = "cart_meal=15,qte=4";
        facade.update(cart);
        assertNotNull(CartStorage.load("cartStorage"));
    }

    @Test
    public void testLoad() throws IOException {
        CartFacade facade = CartFacade.getInstance();
        String cart = "cart_meal=15,qte=4";
        facade.update(cart);
        Object loadedCart = facade.load();
        assertNotNull(loadedCart);
    }

    @Test
    public void testGetInstance() {
        CartFacade facade1 = CartFacade.getInstance();
        CartFacade facade2 = CartFacade.getInstance();
        assertEquals(facade1, facade2);
    }

}

