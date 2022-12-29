package project.business.models;

public class Opinion {


    public int idOpinion;
    public int idUser;
    public String comment;

    public Opinion(){ }


    public Opinion(int idOpinion, int idUser, String comment) {
        this.idOpinion = idOpinion;
        this.idUser = idUser;
        this.comment = comment;
    }

    public int getIdOpinion() {
        return idOpinion;
    }


    public void setIdOpinion(int idOpinion) {
        this.idOpinion = idOpinion;
    }


    public int getIdUser() {
        return idUser;
    }


    public void setIdUser(int idUser) {
        this.idUser = idUser;
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
