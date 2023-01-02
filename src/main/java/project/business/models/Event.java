package project.business.models;

import java.sql.Timestamp;

public class Event {

    // Instance variables
    private Integer idEvent;
    private String title;
    private String description;
    private Timestamp date;

    private String time;

    @Override
    public String toString() {
        return "Event{" +
                "idEvent=" + idEvent +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", date=" + date +
                '}';
    }

    public Integer getIdEvent() {
        return idEvent;
    }

    public void setIdEvent(Integer idEvent) {
        this.idEvent = idEvent;
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

    public Timestamp getDate() {return date;}

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }


    public Event(Integer idEvent, String title, String description, Timestamp date) {
        this.idEvent = idEvent;
        this.title = title;
        this.description = description;
        this.date = date;

        // Create a time string from date

        String[] time = date.toString().split(" ");
        this.time = time[1].substring(0,5);
    }


}
