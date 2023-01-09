package project.business.facade;

import javafx.collections.ObservableList;
import project.business.models.Event;
import project.persistence.factory.AbstractDAOFactory;
import project.persistence.factory.PostGresDAOFactory;
import project.persistence.product.abstr.EventDAO;

public class EventFacade {

    // Instance variables
    private EventDAO eventDAO;

    /**
     * Constructor of the class UserFacade
     */
    private EventFacade() {
        // Get the DAO
        AbstractDAOFactory factory = PostGresDAOFactory.getInstance();
        // Get the TableDAO
        this.eventDAO = factory.getEventDAO();
    }



    public Boolean createEvent(Event event) {
        return this.eventDAO.createEvent(event);
    }

    /**
     * This method is used to update an event
     * @param event the Event to update
     * @return a Boolean, true if the Event is updated, false otherwise
     */
    public Boolean updateEvent(Event event) {
        return this.eventDAO.updateEvent(event);
    }


    /**
     * This method is used to get an event by its id
     * @return the Event with the id given
     */
    public Event getEventById(Integer idEvent) {
        return this.eventDAO.getEventById(idEvent);
    }

    /**
     * This method is used to delete an event by its id
     * @return a Boolean, true if the Event is deleted, false otherwise
     */
    public boolean deleteEvent(Integer idEvent) {
        return this.eventDAO.deleteEvent(idEvent);
    }

    /**
     * This method is used to get all the events
     * @return a list of events
     */
    public ObservableList<Event> getAllEvents() {
        return this.eventDAO.getAllEvents();
    }
    /**
     * Get the instance of the class
     * @return the instance of the class
     * it's a singleton
     */
    public static EventFacade getInstance() {
        return EventFacade.FacadeHolder.INSTANCE;
    }


    /**
     * This class is used to get the instance of the class
     * it's a holder class for the singleton
     */
    private static class FacadeHolder {
        // Instance of the class UserFacade
        static final EventFacade INSTANCE = new EventFacade();
    }

}
