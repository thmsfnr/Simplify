package project.persistence.product.abstr;

import javafx.collections.ObservableList;
import project.business.models.Notification;

import java.util.List;

public abstract class NotificationDAO {

    public abstract Boolean insertNotification(Notification notification) ;
    public abstract Boolean deleteNotification(int idNotification) ;

    public abstract ObservableList<Notification> getAllNotifications(int idUser) ;
}
