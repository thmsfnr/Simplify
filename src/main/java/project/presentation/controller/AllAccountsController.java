package project.presentation.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import project.business.facade.UserFacade;
import project.business.models.User;

import java.util.List;

public class AllAccountsController {

    /*
    consultAll(ActionEvent event) : void
update(ActionEvent event) : void
delete(ActionEvent event) : void
create(ActionEvent event) : void
consultAskDelete(ActionEvent event) : void
     */

    // Instance variables
    @FXML
    private List<User> users;

    /**
     * This method is used to load datas of users
     * @param event the event of loading the frame
     */
    public void consultAll(ActionEvent event) {

        UserFacade userFacade = UserFacade.getInstance();

        users = userFacade.getAllUser();
    }

    /**
     * This method is used to manage the event of the update button
     * @param event the event of the update button
     */
    public void update(ActionEvent event) {

    }

    /**
     * This method is used to manage the event of the delete button
     * @param event the event of the delete button
     */
    public void delete(ActionEvent event) {

    }

}
