package project.exceptions;

public class ReservationNotFoundException extends Exception {
    /**
     * Constructor of the class ReservationNotFoundException
     * It is used when the reservation is not found
     */
    public ReservationNotFoundException() {
        super("Reservation not found");
    }
}
