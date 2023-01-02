package project.persistence.product;

import project.business.models.Reservation;
import project.exceptions.AccessDatabaseException;

import java.util.List;

public abstract class ReservationDAO {

    public abstract Boolean create(Reservation reservation) throws AccessDatabaseException;
    public abstract Boolean update(Reservation reservation) ;

    public abstract Boolean delete(int idReservation) ;
    public abstract Reservation getById(int idReservation) ;

    public abstract List<Reservation> getAllReservations(int idRestaurant) ;
    public abstract List<Reservation> getAllReservationsOfUser(int idUser) ;

    public abstract Boolean cancelReservation(Reservation reservation) ;

    public abstract Boolean acceptReservation(Reservation reservation) ;

    public abstract Boolean rejectReservation(Reservation reservation) ;

}
