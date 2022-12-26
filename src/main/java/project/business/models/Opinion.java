package project.business.models;

public class Opinion {
    private int idOpinion;
    private int idUser;
    private String comment;

    public Opinion(int idOpinion, int idUser, String comment) {
        this.idOpinion = idOpinion;
        this.idUser = idUser;
        this.comment = comment;
    }

    public int getIdOpinion() {
        return idOpinion;
    }

    public int getIdUser() {
        return idUser;
    }

    @Override
    public String toString() {
        return idOpinion + " - " + idUser + " - " + comment;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
