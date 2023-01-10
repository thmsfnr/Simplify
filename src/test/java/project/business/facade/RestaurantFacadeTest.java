
package project.business.facade;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class RestaurantFacadeTest {

    @Test
    void testGetInstance(){
        RestaurantFacade restaurantFacade1 = RestaurantFacade.getInstance();
        RestaurantFacade restaurantFacade2 = RestaurantFacade.getInstance();
        Assertions.assertEquals(restaurantFacade1, restaurantFacade2);
    }

}
