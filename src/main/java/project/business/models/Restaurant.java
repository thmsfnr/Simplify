package project.business.models;

public class Restaurant {


    private int idRestaurant;
    private String name;
    private String address;
    private int nbOfStars;
    private int length;
    private int width;
    private String phoneNumber;
    private String email;

    private int idManager;

    public Restaurant() {
    }

    public Restaurant(int idRestaurant, String name, String address, int nbOfStars, int length, int width, String phoneNumber, String email, int idManager) {
        this.idRestaurant = idRestaurant;
        this.name = name;
        this.address = address;
        this.nbOfStars = nbOfStars;
        this.length = length;
        this.width = width;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.idManager = idManager;
    }

    public int getIdRestaurant() {
        return idRestaurant;
    }

    public void setIdRestaurant(int idRestaurant) {
        this.idRestaurant = idRestaurant;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getNbOfStars() {
        return nbOfStars;
    }

    public void setNbOfStars(int nbOfStars) {
        this.nbOfStars = nbOfStars;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getIdManager() {
        return idManager;
    }

    public void setIdManager(int idManager) {
        this.idManager = idManager;


    @Override
    public String toString() {
        return "Restaurant{" +
                "id=" + id +
                '}';
    }
}
