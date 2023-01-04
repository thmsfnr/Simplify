package project.business.facade;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import project.business.models.Meal;
import project.business.models.Opinion;
import project.exceptions.MealNotFoundException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MealFacadeTest {

    @Test
    void create_restaurant_not_exist() {
        MealFacade mealFacade = MealFacade.getInstance();
        Meal meal = new Meal(0,"cc","un truc",11);
        Boolean result = mealFacade.create(meal);
        assertFalse(result);
    }

    @Disabled
    @Test
    void create(){
        MealFacade mealFacade = MealFacade.getInstance();
        Meal meal = new Meal(1,"cc","un truc",11);
        Boolean result = mealFacade.create(meal);
        assertTrue(result);
        Meal meal2 = mealFacade.getById(7);
        assertEquals(meal2.getIdRestaurant(),1);
        assertEquals(meal2.getPrice(),11);
        assertEquals(meal2.getDescription(),"cc");
        assertEquals(meal2.getTitle(),"un truc");
    }



    @Disabled
    @Test
    void delete(){
        MealFacade mealFacade = MealFacade.getInstance();
        Boolean b = mealFacade.delete(2);
        assertTrue(b);
        RuntimeException runtimeException = assertThrows(RuntimeException.class, () -> {
            mealFacade.getById(2);
        });
        assertEquals("project.exceptions.MealNotFoundException: Meal not found",runtimeException.getMessage());
    }

    @Test
    void delete_meal_not_found() {
        MealFacade mealFacade = MealFacade.getInstance();
        Boolean b = mealFacade.delete(0);
        assertFalse(b);
    }

    @Disabled
    @Test
    void update() {
        MealFacade mealFacade = MealFacade.getInstance();
        Meal meal1 = mealFacade.getById(2);
        Meal meal2 = new Meal(2,1,"jecrit rien","soupe",23);
        Boolean result  = mealFacade.update(meal2);
        assertTrue(result);
        Meal meal3 = mealFacade.getById(2);
        assertEquals(meal2.getIdMeal(),meal3.getIdMeal());
        assertEquals(meal2.getIdRestaurant(),meal3.getIdRestaurant());
        assertEquals(meal2.getTitle(),meal3.getTitle());
        assertEquals(meal2.getPrice(),meal3.getPrice());
        assertEquals(meal2.getDescription(),meal3.getDescription());
        assertNotEquals(meal1.getPrice(),meal3.getPrice());
        assertNotEquals(meal1.getDescription(),meal3.getDescription());
    }

    @Test
    void getAllMeal_restaurant_not_found() {
        MealFacade mealFacade = MealFacade.getInstance();
        List<Meal> meals = mealFacade.getAllMeal(0);
        assertEquals(meals.size(),0);
    }

    @Test
    void getAllMeal(){
        MealFacade mealFacade = MealFacade.getInstance();
        List<Meal> meals = mealFacade.getAllMeal(1);
        for (Meal m: meals) {
            assertEquals(m.getIdRestaurant(),1);
            assertNotNull(m.getTitle());
            assertNotNull(m.getPrice());
        }
    }

    @Test
    void getById_meal_not_found() {
        MealFacade mealFacade = MealFacade.getInstance();
        RuntimeException runtimeException = assertThrows(RuntimeException.class, () -> {
            mealFacade.getById(0);
        });
       assertEquals("project.exceptions.MealNotFoundException: Meal not found",runtimeException.getMessage());
    }

    @Test
    void getById(){
        MealFacade mealFacade = MealFacade.getInstance();
        Meal meal = mealFacade.getById(1);
        assertNotNull(meal);
        assertNotNull(meal.getIdMeal());
        assertEquals(meal.getIdMeal(),1);
        assertNotNull(meal.getPrice());
        assertNotNull(meal.getTitle());
    }

    @Test
    void getAllOpinionOfMeal_meal_not_found() {
        MealFacade mealFacade = MealFacade.getInstance();
        List<Opinion> opinions = mealFacade.getAllOpinionOfMeal(0);
        RuntimeException runtimeException = assertThrows(RuntimeException.class, () -> {
            mealFacade.getById(0);
        });
        assertEquals("project.exceptions.MealNotFoundException: Meal not found",runtimeException.getMessage());
    }

    @Test
    void getAllOpinionOfMeal(){
        MealFacade mealFacade = MealFacade.getInstance();
        List<Opinion> opinions = mealFacade.getAllOpinionOfMeal(1);
        assertEquals(opinions.size(),2);
        for (Opinion o:opinions) {
            assertNotNull(o.getIdOpinion());
            assertNotNull(o.getComment());
        }
    }
}