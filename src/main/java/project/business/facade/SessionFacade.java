
package project.business.facade;

import project.business.services.User;
import project.presentation.Action;

public class SessionFacade {

    private User user;
    private Action userInterface;

    public SessionFacade(Action userInterface) {
        this.user = new User();
        this.userInterface = userInterface;
    }

    public void login(String username, String password) {
        this.userInterface.display(this.user.login(username, password));
    }

}
