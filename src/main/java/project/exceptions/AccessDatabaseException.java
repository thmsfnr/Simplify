package project.exceptions;

public class AccessDatabaseException extends Exception {

    public AccessDatabaseException() {
        super("Erreur lors de l'accés à la base de données");
    }

    public AccessDatabaseException(String message, Throwable cause) {
        super(message, cause);
    }

    public AccessDatabaseException(Throwable cause) {
        super(cause);
    }
}
