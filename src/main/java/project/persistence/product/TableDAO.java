package project.persistence.product;

import javafx.collections.ObservableList;
import project.business.models.Table;

import java.util.ArrayList;

public abstract class TableDAO {

    public abstract Boolean insertTable(String name, String description);

    public abstract ObservableList<Table> getAllTables();

    public abstract Boolean deleteTable(int id);
}
