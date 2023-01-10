
package project.business.facade;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TableFacadeTest {

    @Test
    void testGetInstance(){
        TableFacade tableFacade1 = TableFacade.getInstance();
        TableFacade tableFacade2 = TableFacade.getInstance();
        Assertions.assertEquals(tableFacade1, tableFacade2);
    }

}
