package DataAccess;

/**
 * Created by GrantRowberry on 2/23/17.
 */

public class DatabaseException extends Exception {
    public DatabaseException(String s) {
        super(s);
    }

    public DatabaseException(String s, Throwable throwable) {
        super(s, throwable);
    }
}
