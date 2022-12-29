
package project.business.facade;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import project.business.models.Opinion;
import java.util.List;

/**
 * Created by Simplify members on 29/12/22.
 * This is the Test class of the facade of the opinion
 * It is used to test the methods of the DAO to get the informations of the opinion
 * @author Simplify members
 */
public class OpinionFacadeTest {

    @Test
    public void create_success(){
        OpinionFacade opinionFacade = OpinionFacade.getInstance();
        Opinion opinion = new Opinion(7,"test");
        Boolean result = opinionFacade.createOpinion(opinion);
        Assertions.assertTrue(result);
    }

    @Test
    public void create_fail(){
        OpinionFacade opinionFacade = OpinionFacade.getInstance();
        Opinion opinion = new Opinion(0,"test");
        Boolean result = opinionFacade.createOpinion(opinion);
        Assertions.assertFalse(result);
    }

    @Test
    public void get_all(){
        OpinionFacade opinionFacade = OpinionFacade.getInstance();
        List<Opinion> result = opinionFacade.getAllOpinions();
        Assertions.assertTrue(result != null);
    }

    @Test
    public void get_user_success(){
        OpinionFacade opinionFacade = OpinionFacade.getInstance();
        List<Opinion> result = opinionFacade.getAllOpinionsOfUser(7);
        Assertions.assertTrue(result.size() != 0);
    }

    @Test
    public void get_user_fail(){
        OpinionFacade opinionFacade = OpinionFacade.getInstance();
        List<Opinion> result = opinionFacade.getAllOpinionsOfUser(0);
        Assertions.assertTrue(result.size() == 0);
    }

    @Test
    public void update(){
        OpinionFacade opinionFacade = OpinionFacade.getInstance();
        Opinion opinion = new Opinion(7,7,"test");
        Boolean result = opinionFacade.updateOpinion(opinion);
        Assertions.assertTrue(result);
    }

    @Test
    public void delete(){
        OpinionFacade opinionFacade = OpinionFacade.getInstance();
        Boolean result = opinionFacade.deleteOpinion(7);
        Assertions.assertTrue(result);
    }

}
