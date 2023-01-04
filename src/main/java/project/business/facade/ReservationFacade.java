package project.business.facade;

import javafx.collections.ObservableList;
import project.business.models.Meal;
import project.business.models.Reservation;
import project.business.models.Table;
import project.exceptions.AccessDatabaseException;
import project.exceptions.ReservationNotFoundException;
import project.persistence.factory.AbstractDAOFactory;
import project.persistence.factory.PostGresDAOFactory;
import project.persistence.product.abstr.MealDAO;
import project.persistence.product.abstr.ReservationDAO;
import project.persistence.product.abstr.NotificationDAO;
import project.persistence.product.abstr.TableDAO;

import java.util.List;

public class ReservationFacade {
    private MealDAO mealDAO;
    private NotificationDAO notificationDAO;

    private ReservationDAO reservationDAO;

    private TableDAO tableDAO;
    private ReservationFacade() {
        AbstractDAOFactory factory = PostGresDAOFactory.getInstance();
        this.mealDAO = factory.getMealDAO();
        this.tableDAO = factory.getTableDAO();
        this.reservationDAO = factory.getReservationDAO();
        this.notificationDAO = factory.getNotificationDAO();
    }

    public List<Reservation> getAllReservations(int idRestaurant){return reservationDAO.getAllReservations(idRestaurant);}
    public ObservableList<Reservation> getAllReservationsOfUser(int idUser){return reservationDAO.getAllReservationsOfUser(idUser);}

    public Boolean createReservation(Reservation reservation) throws AccessDatabaseException {return reservationDAO.create(reservation);}

    public Boolean updateReservation(Reservation reservation) throws AccessDatabaseException {return reservationDAO.update(reservation);}
    public Boolean deleteReservation(int idReservation){return reservationDAO.delete(idReservation);}

    public Boolean cancelReservation(Reservation reservation){return reservationDAO.cancelReservation(reservation);}

    public Boolean acceptReservation(Reservation reservation){return reservationDAO.acceptReservation(reservation);}

    public Reservation getReservationById(int idReservation) throws ReservationNotFoundException {return reservationDAO.getById(idReservation);}

    public List<Meal> getMealsOfReservation(int idReservation){ return mealDAO.getMealsOfReservation(idReservation);}

    public List<Table> getTablesOfReservation(int idReservation) throws AccessDatabaseException {return tableDAO.getTablesOfReservation(idReservation);}

    public String getStateLabel(int idState){return reservationDAO.getStateLabel(idState);}
    public static ReservationFacade getInstance() {
        return ReservationFacade.FacadeHolder.INSTANCE;
    }

    /**
     *  This class is used to get the instance of the class ReservationFacade
     *  for thread safety reasons (double checked locking)
     */
    private static class FacadeHolder {
        // Instance of the class ReservationFacade
        static final ReservationFacade INSTANCE = new ReservationFacade();
    }
}
