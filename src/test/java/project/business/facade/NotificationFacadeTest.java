package project.business.facade;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import project.business.models.Meal;
import project.business.models.Notification;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class NotificationFacadeTest {

    @Disabled
    @Test
    void createNotification() {
        NotificationFacade notificationFacade = NotificationFacade.getInstance();
        Notification notification = new Notification(7,"test","je sais pas");
        Boolean result = notificationFacade.createNotification(notification);
        assertTrue(result);
    }

    @Test
    void createNotification_user_not_exist() {
        NotificationFacade notificationFacade = NotificationFacade.getInstance();
        Notification notification = new Notification(-1,"test","je sais pas");
        Boolean result = notificationFacade.createNotification(notification);
        assertFalse(result);
    }
    @Test
    void deleteNotification_idNotification_not_exist() {
        NotificationFacade notificationFacade = NotificationFacade.getInstance();
        Boolean b = notificationFacade.deleteNotification(2);
        assertFalse(b);
    }

    @Disabled
    @Test
    void deleteNotification(){
        NotificationFacade notificationFacade = NotificationFacade.getInstance();
        Boolean b = notificationFacade.deleteNotification(1);
        assertTrue(b);
    }

    @Test
    void getAllNotifications() {
        NotificationFacade notificationFacade = NotificationFacade.getInstance();
        List<Notification> notifications = notificationFacade.getAllNotifications(7);
        for (Notification n: notifications) {
            assertEquals(n.getIdUser(),7);
            assertNotNull(n.getTitle());
        }
    }
    @Test
    void getAllNotifications_user_not_exist(){
        NotificationFacade notificationFacade = NotificationFacade.getInstance();
        List<Notification> notifications = notificationFacade.getAllNotifications(1);
        assertEquals(notifications.size(),0);
    }
}