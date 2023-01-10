
package project.business.facade;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import project.business.models.Delivery;
import project.business.models.State;
import project.exceptions.AccessDatabaseException;

import java.sql.Date;
import java.util.List;

public class DeliveryFacadeTest {

    @Test
    void create() {
        DeliveryFacade deliveryFacade = DeliveryFacade.getInstance();
        try {
            Delivery delivery = new Delivery(0, 1, 1, new Date(2023,11,12), State.ON_DELIVERY);
            deliveryFacade.createDelivery(delivery, null);
            List<Delivery> deliveries = deliveryFacade.getAllDeliveriesOfUser(1);
            int last_index = deliveries.size() - 1;
            Delivery last_delivery = deliveries.get(last_index);


            Assertions.assertEquals(last_delivery.getIdRestaurant(), delivery.getIdRestaurant());
            Assertions.assertEquals(last_delivery.getState(), delivery.getState());
            Assertions.assertEquals(last_delivery.getIdUser(), delivery.getIdUser());
            Assertions.assertNotEquals(last_delivery.getIdDelivery(), delivery.getIdDelivery());
        } catch (AccessDatabaseException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void create2(){
        DeliveryFacade deliveryFacade = DeliveryFacade.getInstance();

        try{
            Delivery delivery = new Delivery(0, 1, 1, null, State.IN_PREPARATION);
            deliveryFacade.createDelivery(delivery, null);
            List<Delivery> deliveries = deliveryFacade.getAllDeliveries();
            int last_index = deliveries.size() - 1;
            Delivery last_delivery = deliveries.get(last_index);
            last_delivery.setIdUser(5);
            deliveryFacade.updateDelivery(last_delivery);
            Delivery delivery2 = deliveryFacade.getDeliveryById(last_delivery.getIdDelivery());
            deliveryFacade.deleteDelivery(delivery2.getIdDelivery());
            Delivery delivery3 = deliveryFacade.getDeliveryById(last_delivery.getIdDelivery());

            Assertions.assertTrue(delivery2.getIdUser() == 5);
            Assertions.assertFalse(last_delivery.getIdUser() != 5);
            Assertions.assertEquals(delivery3, null);

        }
        catch(AccessDatabaseException e){
            throw new RuntimeException(e);
        }
    }

    @Test
    void getForUser(){
        DeliveryFacade deliveryFacade = DeliveryFacade.getInstance();

        try {
            int idUser = 3;
            List<Delivery> deliveries = deliveryFacade.getAllDeliveriesOfUser(idUser);

            deliveries.forEach(delivery -> {
                Assertions.assertEquals(delivery.getIdUser(), idUser);
            });
        }
        catch(AccessDatabaseException e){
            throw new RuntimeException("Erreur de communication avec la base de donnée");
        }
    }

    @Test
    void changeStateTest(){
        DeliveryFacade deliveryFacade = DeliveryFacade.getInstance();

        try {
            Delivery delivery = new Delivery(0, 1, 3, null, State.WAITING_FOR_VALIDATION);
            deliveryFacade.createDelivery(delivery, null);
            List<Delivery> deliveries = deliveryFacade.getAllDeliveries();
            int last_index = deliveries.size() - 1;
            Delivery last_delivery = deliveries.get(last_index);
            deliveryFacade.changeStateOfDelivery(last_delivery.getIdDelivery(), State.DELIVERED.toString());
            Delivery delivery2 = deliveryFacade.getDeliveryById(last_delivery.getIdDelivery());

            Assertions.assertEquals(delivery2.getState(), State.DELIVERED);
            Assertions.assertEquals(delivery2.getIdDelivery(), last_delivery.getIdDelivery());

        }
        catch(AccessDatabaseException e){
            throw new RuntimeException("Erreur lors de la communication avec la base de données");
        }
    }

    @Test
    void getDeliveriesOfRestaurant(){
        DeliveryFacade deliveryFacade = DeliveryFacade.getInstance();

        try{
            int idRestaurant = 3;
            deliveryFacade.getAllDeliveriesOfRestaurant(idRestaurant).forEach(delivery -> {
                Assertions.assertEquals(delivery.getIdRestaurant(), idRestaurant);
            });
        }
        catch(AccessDatabaseException e){
            throw new RuntimeException("Erreur lors de l'accés à la base de données");
        }
    }

}
