package project.business.facade;

import javafx.collections.ObservableList;
import project.business.models.Notification;
import project.persistence.factory.AbstractDAOFactory;
import project.persistence.factory.PostGresDAOFactory;
import project.persistence.product.abstr.NotificationDAO;

public class NotificationFacade {
    private NotificationDAO notificationDAO;

    public Boolean createNotification(Notification notification) {return this.notificationDAO.insertNotification(notification);}

    public Boolean deleteNotification(int idNotification) {return this.notificationDAO.deleteNotification(idNotification);}

    public ObservableList<Notification> getAllNotifications(int idUser) {return this.notificationDAO.getAllNotifications(idUser);}

    private NotificationFacade() {
        AbstractDAOFactory factory = PostGresDAOFactory.getInstance();
        this.notificationDAO = factory.getNotificationDAO();
    }
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
