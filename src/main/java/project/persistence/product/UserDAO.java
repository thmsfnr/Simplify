package project.persistence.product;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Objects;

public abstract class UserDAO {
    public abstract ArrayList<Object> getByMail(String mail);

}
