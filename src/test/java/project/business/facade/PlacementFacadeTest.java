
package project.business.facade;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import project.business.models.Table;
import project.exceptions.AccessDatabaseException;
import java.util.List;

public class PlacementFacadeTest {

    @Test
    void deletePlacement(){
        PlacementFacade placementFacade = PlacementFacade.getInstance();

        try{
            int idRestaurant = 2;
            List<Table> tables = placementFacade.getAllTablesOfRestaurant(idRestaurant);
            Table table = tables.get(0);
            placementFacade.deletePlacement(table.getIdTable());
            Table new_table = placementFacade.getTableById(table.getIdTable());


            Assertions.assertEquals(new_table.getX(), -1);
            Assertions.assertEquals(new_table.getY(), -1);

        }
        catch(AccessDatabaseException e){
            throw new RuntimeException(e);
        }
    }

    @Test
    void updatePlacement(){
        PlacementFacade placementFacade = PlacementFacade.getInstance();

        try{
            int idRest = 1;
            List<Table> tables = placementFacade.getAllTablesOfRestaurant(idRest);
            Table table = tables.get(0);
            placementFacade.updatePlacement(table.getIdTable(), 1, 1);
            Table new_table = placementFacade.getTableById(table.getIdTable());

            Assertions.assertEquals(new_table.getY(), 1);
            Assertions.assertEquals(new_table.getX(), 1);
        }
        catch(AccessDatabaseException e){
            throw new RuntimeException(e);
        }
    }

    @Test
    void deletePlacementTest(){
        PlacementFacade placementFacade = PlacementFacade.getInstance();
        int idTable = -5;

        Assertions.assertThrows(AccessDatabaseException.class, () -> {
            placementFacade.deletePlacement(idTable);
        });
    }

}
