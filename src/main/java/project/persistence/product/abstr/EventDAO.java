package project.persistence.product.abstr;

import javafx.collections.ObservableList;
import project.business.models.Event;

public abstract class EventDAO {


    public  abstract Boolean createEvent(Event event);
    /**
     * This method is used to update an event
     * @param event the event to update
     * @return a boolean, true if the event is updated, false otherwise
     */
    public abstract Boolean updateEvent(Event event);

    /**
     * This method is used to get an event by its id
     * @return the Event with the id given
     */
    public abstract Event getEventById(Integer idEvent);

    /**
     * This method is used to delete an event by its id
     * @return a Boolean, true if the Event is deleted, false otherwise
     */
    public abstract boolean deleteEvent(Integer idEvent);

    /**
     * This method is used to get all the events
     * @return a list of events
     */
    public abstract ObservableList<Event> getAllEvents();
}


