
package project.business.models;

/**
 * This class is used to represent an opinion
 * @author Simplify members
 */
public class Opinion {

    // Instance variables
    public int idOpinion;
    public int idUser;
    public String comment;

    /**
     * This method is used to create a blank opinion
     */
    public Opinion(){ }


    /**
     * This method is used to create an opinion
     * @param idOpinion the id of the opinion
     * @param idUser the id of the user
     * @param comment the comment of the opinion
     */
    public Opinion(int idOpinion, int idUser, String comment) {
        this.idOpinion = idOpinion;
        this.idUser = idUser;
        this.comment = comment;
    }

    /**
     * This method is used to create an opinion
     * @param idUser the id of the user
     * @param comment the comment of the opinion
     * @return the opinion created
     */
    public Opinion(int idUser, String comment) {
        this.idUser = idUser;
        this.comment = comment;
    }

    /**
     * This method is used to get the id of the opinion
     * @return the id of the opinion
     */
    public int getIdOpinion() {
        return idOpinion;
    }

    /**
     * This method is used to set the id of the opinion
     * @param idOpinion the id of the opinion
     */
    public void setIdOpinion(int idOpinion) {
        this.idOpinion = idOpinion;
    }

    /**
     * This method is used to get the id of the user
     * @return the id of the user
     */
    public int getIdUser() {
        return idUser;
    }

    /**
     * This method is used to set the id of the user
     * @param idUser the id of the user
     */
    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    /**
     * This method id used to transform the opinion into a string
     * @return the string of the opinion
     */
    @Override
    public String toString() {
        return idOpinion + " - " + idUser + " - " + comment;

    }

    /**
     * This method is used to get the comment of the opinion
     * @return the comment of the opinion
     */
    public String getComment() {
        return comment;
    }

    /**
     * This method is used to set the comment of the opinion
     * @param comment the comment of the opinion
     */
    public void setComment(String comment) {
        this.comment = comment;
    }

}
