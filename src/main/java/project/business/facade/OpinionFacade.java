
package project.business.facade;

import project.business.models.Opinion;
import project.persistence.factory.AbstractDAOFactory;
import project.persistence.factory.PostGresDAOFactory;
import project.persistence.product.OpinionDAO;
import java.util.List;

/**
 * This class is the facade of the opinion
 * It is used to call the methods of the database to get the informations of the opinion
 * @author Simplify members
 */
public class OpinionFacade {

    // Instance variables
    private static OpinionFacade instance;
    private OpinionDAO opinionDAO;

    /**
     * Constructor of the class OpinionFacade
     */
    private OpinionFacade() {
        // Get the DAO
        AbstractDAOFactory factory = PostGresDAOFactory.getInstance();
        // Get the UserDAO
        this.opinionDAO = factory.getOpinionDAO();
    }

    /**
     * This method is used to get the instance of the class OpinionFacade
     * @return the instance of the class PaymentFacade
     */
    public static OpinionFacade getInstance() {
        if (instance == null) {
            instance = new OpinionFacade();
        }
        return instance;
    }

    /**
     * This method is used to create an opinion
     * @param opinion the opinion to create
     * @return true if the opinion is created, false otherwise
     */
    public boolean createOpinion(Opinion opinion) {
        return opinionDAO.create(opinion);
    }

    /**
     * This method is used to get all the opinions
     * @return the list of all the opinions
     */
    public List<Opinion> getAllOpinions() {
        return opinionDAO.getAll();
    }

    /**
     * This method is used to get all the opinions of a user
     * @param id the id of the user
     * @return the list of all the opinions of a user
     */
    public List<Opinion> getAllOpinionsOfUser(int id) {
        return opinionDAO.getByUser(id);
    }

    /**
     * This method is used to update an opinion
     * @param opinion the opinion to update
     * @return true if the opinion is updated, false otherwise
     */
    public boolean updateOpinion(Opinion opinion) {
        return opinionDAO.update(opinion);
    }

    /**
     * This method is used to delete an opinion
     * @param id the id of the opinion to delete
     * @return true if the opinion is deleted, false otherwise
     */
    public boolean deleteOpinion(int id) {
        return opinionDAO.delete(id);
    }

}
