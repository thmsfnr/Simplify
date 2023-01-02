package project.business.models;


import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Reservation {
    private int idOrder;

    private int idTypeOrder;
    private int idRestaurant;
    private int idUser;
    private Date date;
    private List<Integer> tables;
    private Map<Integer,Integer> meals;
    private int idState;


    public Reservation(int idOrder, int idRestaurant, int idUser, Date date, int idState) {
        this.idOrder = idOrder;
        this.idRestaurant = idRestaurant;
        this.idUser = idUser;
        this.date = date;
        this.tables = new ArrayList<>();
        this.meals = new HashMap<>();
        this.idState = idState;
        this.idTypeOrder = 2;
    }

    public int getIdOrder() {
        return idOrder;
    }

    public int getIdTypeOrder() {
        return idTypeOrder;
    }

    public void setIdOrder(int idOrder) {
        this.idOrder = idOrder;
    }

    public int getIdRestaurant() {
        return idRestaurant;
    }

    public int getIdUser() {
        return idUser;
    }

    public List<Integer> getTables() {
        return tables;
    }

    public Map<Integer, Integer> getMeals() {
        return meals;
    }

    public void addTable(int idTable){
        tables.add(idTable);
    }
    public void addMeal(int idMeal, int quantity){
        meals.put(idMeal,quantity);
    }

    public void removeTable(int idTable){
        tables.remove(idTable);
    }
    public void removeMeal(int idMeal){
        meals.remove(idMeal);
    }
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getIdState() {
        return idState;
    }

    public void setIdState(int idState) {
        this.idState = idState;
    }
}
