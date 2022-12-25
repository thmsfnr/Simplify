package project.business.models;

public class Restaurant {

    private int id;

    public int getId() {
        return id;
    }

    public Restaurant(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Restaurant{" +
                "id=" + id +
                '}';
    }
}
