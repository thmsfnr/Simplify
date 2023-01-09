package project.business.models;

/**
 * Created by Simplify members on 22/12/22.
 * This class is the model of the table
 * It is used to create a table
 *
 * @author Simplify members
 */
public class Table {

    // Instance variables
    private Integer idTable;
    private String name;
    private String description;
    private Boolean booked;
    private int x;
    private int y;

    private int idRestaurant;

    // Constructor
    public Table(Integer idTable, String name, String description, Boolean booked, int x, int y) {
        this.idTable = idTable;
        this.name = name;
        this.description = description;
        this.booked = booked;
        this.x = x;
        this.y = y;
    }

    public Table(int idTable,int idRestaurant, String name, String description, Boolean booked, int x, int y) {
        this.idTable = idTable;
        this.idRestaurant = idRestaurant;
        this.name = name;
        this.description = description;
        this.booked = booked;
        this.x = x;
        this.y = y;
    }

    // Getters and setters
    public Integer getIdTable() {
        return idTable;
    }

    public void setIdTable(Integer idTable) {
        this.idTable = idTable;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getBooked() {
        return booked;
    }

    public void setBooked(Boolean booked) {
        this.booked = booked;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    @Override
    public String toString() {
        return "Table{" +
                "idTable=" + idTable +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", booked=" + booked +
                '}';
    }

    public int getIdRestaurant() {
        return idRestaurant;
    }

}
