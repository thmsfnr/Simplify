package project.exceptions;

public class MealNotFoundException extends Exception{

    /**
     * Constructor of the class MealNotFoundException
     * It is used when the meal is not found
     */
    public MealNotFoundException() {
        super("Meal not found");
    }

    
}
