package project.business.facade;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import project.business.models.Delivery;
import project.business.models.State;

import java.sql.Date;

public class DeliveryFacadeTest {

    @Test
    void create() {
        DeliveryFacade deliveryFacade = DeliveryFacade.getInstance();
        Delivery delivery = new Delivery(0, 1, 1, new Date(2023,11,12), State.ON_DELIVERY);
        Assertions.assertThrows(RuntimeException.class, () -> {
            deliveryFacade.createDelivery(delivery);
        });

    }
}
