
package project.persistence.product;

import project.business.models.User;

public abstract class UserDAO {
    public abstract User getByEmail(String email);

}
