package project.persistence.product;

import project.business.models.Opinion;
import project.exceptions.AccessDatabaseException;

import java.util.ArrayList;

public abstract class OpinionDAO {

    public abstract ArrayList<Opinion> getOpinionsOfRestaurant(int id) throws AccessDatabaseException;
}
