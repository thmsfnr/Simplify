package project.business.models;


import java.sql.Date;
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


    public Reservation(int idOrder, int idRestaurant, int idUser, Date date, List<Integer> tables, Map<Integer, Integer> meals, int idState) {
        this.idOrder = idOrder;
        this.idRestaurant = idRestaurant;
        this.idUser = idUser;
        this.date = date;
        this.tables = tables;
        this.meals = meals;
        this.idState = idState;
    }

    public int getIdOrder() {
        return idOrder;
    }

    public int getIdTypeOrder() {
        return idTypeOrder;
    }

    public void setIdTypeOrder(int idTypeOrder) {
        this.idTypeOrder = idTypeOrder;
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
