package project.business.models;

public class Opinion {
    private int idOpinion;
    private int idUser;
    private String description;

    public Opinion(int idOpinion, int idUser, String description) {
        this.idOpinion = idOpinion;
        this.idUser = idUser;
        this.description = description;
    }

    public int getIdOpinion() {
        return idOpinion;
    }

    public int getIdUser() {
        return idUser;
    }


    @Override
    public String toString() {
        return "Opinion{" +
                "idOpinion=" + idOpinion +
                ", idUser=" + idUser +
                ", description='" + description + '\'' +
                '}';
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
