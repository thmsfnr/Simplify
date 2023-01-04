package project.business.models;

public class Notification {
    private int idNotification;

    private int idUser;
    private String title;
    private String description;

    public int getIdNotification() {
        return idNotification;
    }

    public Notification(int idNotification,String title, String description,int idUser) {
        this.idNotification = idNotification;
        this.idUser = idUser;
        this.title = title;
        this.description = description;
    }

    public Notification(int idUser, String title, String description) {
        this.idUser = idUser;
        this.title = title;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public String toString() {
        return "Notification{" +
                "idNotification=" + idNotification +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

    public int getIdUser() {
        return idUser;
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
}
