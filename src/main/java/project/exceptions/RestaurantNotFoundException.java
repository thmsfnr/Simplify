package project.exceptions;

public class RestaurantNotFoundException extends Exception {

    public RestaurantNotFoundException() {
        super("Restaurant not found");
    }

    public RestaurantNotFoundException(String message) {
        super(message);
    }
}

