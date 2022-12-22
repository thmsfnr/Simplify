package project.persistence.product;

import project.business.models.Opinion;

import java.util.List;

public abstract class OpinionDAO {

    public abstract List<Opinion> getAllOpinionOfMeal(int idMeal);
}
