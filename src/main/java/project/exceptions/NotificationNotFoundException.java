package project.exceptions;

public class NotificationNotFoundException extends Exception{

        /**
        * Constructor of the class NotificationNotFoundException
        * It is used when the notification is not found
        */
        public NotificationNotFoundException() {
            super("Notification not found");
        }
}
