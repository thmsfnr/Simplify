
package project.business.facade;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class EventFacadeTest {

    @Test
    public void testGetInstance() {
        EventFacade facade1 = EventFacade.getInstance();
        EventFacade facade2 = EventFacade.getInstance();
        assertEquals(facade1, facade2);
    }


}
