package project.business.models;

public class Meal {
    private int idMeal;
    private int idRestaurant;
    private String description;
    private String title;
    private double price;
    private String image;

    public Meal(int idMeal, int idRestaurant, String description, String title, double price) {
        this.title = title;
        this.description = description;
        this.price = price;
        this.idRestaurant = idRestaurant;
        this.idMeal = idMeal;
    }

    public Meal(int idRestaurant, String description, String title, double price) {
        this.title = title;
        this.description = description;
        this.price = price;
        this.idRestaurant = idRestaurant;
    }
    public int getIdMeal() {
        return idMeal;
    }

    public int getIdRestaurant() {
        return idRestaurant;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    @Override
    public String toString() {
        return "Meal{" +
                "idMeal=" + idMeal +
                ", idRestaurant=" + idRestaurant +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", image='" + image + '\'' +
                '}';
    }

    public void setImage(String image) {
        this.image = image;
    }

}
