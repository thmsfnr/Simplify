package project.persistence.product;

import project.exceptions.UserNotFoundException;
import project.models.UserModel;

public interface UserDAO {
    UserModel getByUsername(String username) throws UserNotFoundException;

    void delete(String username);

    void update(UserModel user);

}