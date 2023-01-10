
package project.business.facade;

import project.business.models.Notification;
import project.persistence.factory.AbstractDAOFactory;
import project.persistence.factory.PostGresDAOFactory;
import project.persistence.product.abstr.NotificationDAO;
import java.util.List;

/**
 * Created by Simplify members on 10/01/23.
 * This class is the facade of the notification
 * @author Simplify members
 */
public class NotificationFacade {

    private NotificationDAO notificationDAO;

    /**
     * This method is used to create a notification
     * @param notification the notification to create
     * @return True if the notification is created, false otherwise
     */
    public Boolean createNotification(Notification notification) {
        return this.notificationDAO.insertNotification(notification);
    }

    /**
     * This method is used to delete a notification by its id
     * @param idNotification the id of the notification to delete
     * @return a Boolean, true if the notification is deleted, false otherwise
     */
    public Boolean deleteNotification(int idNotification) {
        return this.notificationDAO.deleteNotification(idNotification);
    }

    /**
     * This method is used to get all the notifications of a user
     * @param idUser the id of the user
     * @return a list of notifications
     */
    public List<Notification> getAllNotifications(int idUser) {
        return this.notificationDAO.getAllNotifications(idUser);
    }

    /**
     * This method is used to crete a notification facade
     */
    private NotificationFacade() {
        AbstractDAOFactory factory = PostGresDAOFactory.getInstance();
        this.notificationDAO = factory.getNotificationDAO();
    }

    /**
     * This method is used to get the instance of the notification facade
     * @return a notification facade
     */
    public static NotificationFacade getInstance() {
        return NotificationFacade.FacadeHolder.INSTANCE;
    }

    /**
     *  This class is used to get the instance of the class MealFacade
     *  for thread safety reasons (double checked locking)
     */
    private static class FacadeHolder {
        // Instance of the class UserFacade
        static final NotificationFacade INSTANCE = new NotificationFacade();
    }

}
