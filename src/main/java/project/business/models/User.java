package project.business.models;

/**
 * Created by Simplify members on 07/12/22.
 * This class is the model of the user
 * It is used to store the informations of the user
 * @author Simplify members
 */
public class User {

    // Instance variables
    private Integer id;
    private String password;
    private String email;
    private String name;
    private String firstname;
    private String address;
    private String phone;

    /**
     * Constructor of the class User
     * @param id the id of the user
     * @param password the password of the user
     * @param email the email of the user
     * @param name the name of the user
     * @param firstname the firstname of the user
     * @param address the address of the user
     * @param phone the phone number of the user
     */
     public User(Integer id, String password, String email, String name, String firstname, String address, String phone) {
        this.id = id;
        this.password = password;
        this.email = email;
        this.name = name;
        this.firstname = firstname;
        this.address = address;
        this.phone = phone;
    }

    /**
     * This method is used to get the id of the user
     * @return the id of the user
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method is used to set the id of the user
     * @param id the id of the user
     */
    public void setUsername(Integer id) {
        this.id = id;
    }

    /**
     * This method is used to get the password of the user
     * @return the password of the user
     */
    public String getPassword() {
        return password;
    }

    /**
     * This method is used to set the password of the user
     * @param password the password of the user
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * This method is used to get the email of the user
     * @return the email of the user
     */
    public String getEmail() {
        return email;
    }

    /**
     * This method is used to set the email of the user
     * @param email the email of the user
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * This method is used to get the name of the user
     * @return the name of the user
     */
    public String getName() {
        return name;
    }

    /**
     * This method is used to set the name of the user
     * @param name the name of the user
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * This method is used to get the firstname of the user
     * @return the firstname of the user
     */
    public String getFirstname() {
        return firstname;
    }

    /**
     * This method is used to set the firstname of the user
     * @param firstname the firstname of the user
     */
    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    /**
     * This method is used to get the address of the user
     * @return the address of the user
     */
    public String getAddress() {
        return address;
    }

    /**
     * This method is used to set the address of the user
     * @param address the address of the user
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * This method is used to get the phone number of the user
     * @return the phone number of the user
     */
    public String getPhone() {
        return phone;
    }

    /**
     * This method is used to set the phone number of the user
     * @param phone the phone number of the user
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

}
