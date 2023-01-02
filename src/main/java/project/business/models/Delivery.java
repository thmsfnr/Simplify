package project.business.models;

import java.sql.Date;

public class Delivery {
    private int idDelivery;
    private int idRestaurant;
    private int idUser;
    private Date date;
    private State state;


    public Delivery(){ }
    public Delivery(int idDelivery, int idRestaurant, int idUser, Date date, State state) {
        this.idDelivery = idDelivery;
        this.idRestaurant = idRestaurant;
        this.idUser = idUser;
        this.date = date;
        this.state = state;
    }

    public int getIdDelivery() {
        return idDelivery;
    }

    public void setIdDelivery(int idDelivery) {
        this.idDelivery = idDelivery;
    }

    public int getIdRestaurant() {
        return idRestaurant;
    }

    public void setIdRestaurant(int idRestaurant) {
        this.idRestaurant = idRestaurant;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }
}
