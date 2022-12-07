
package project.persistence.product;

import project.models.User;

import java.util.ArrayList;
public abstract class UserDAO {
    public abstract User getByEmail(String email);

}
