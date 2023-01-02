package project.business.facade;

import project.business.models.Meal;
import project.business.models.Reservation;
import project.business.models.Table;
import project.persistence.factory.AbstractDAOFactory;
import project.persistence.factory.PostGresDAOFactory;
import project.persistence.product.MealDAO;
import project.persistence.product.ReservationDAO;
import project.persistence.product.NotificationDAO;
import project.persistence.product.TableDAO;

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
    public List<Reservation> getAllReservationsOfUser(int idUser){return reservationDAO.getAllReservationsOfUser(idUser);}

    public Boolean createReservation(Reservation reservation){return reservationDAO.create(reservation);}

    public Boolean updateReservation(Reservation reservation){return reservationDAO.update(reservation);}
    public Boolean deleteReservation(int idReservation){return reservationDAO.delete(idReservation);}

    public Boolean cancelReservation(Reservation reservation){return reservationDAO.cancelReservation(reservation);}

    public Boolean acceptReservation(Reservation reservation){return reservationDAO.acceptReservation(reservation);}

    public Boolean rejectReservation(Reservation reservation){return reservationDAO.rejectReservation(reservation);}

    public Reservation getReservationById(int idReservation){return reservationDAO.getById(idReservation);}

    public List<Meal> getMealsOfReservation(int idReservation){ return mealDAO.getMealsOfReservation(idReservation);}

    public List<Table> getTablesOfReservation(int idReservation){return tableDAO.getTablesOfReservation(idReservation);}
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
