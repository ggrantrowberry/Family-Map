package Services;

import DataAccess.DatabaseException;
import DataAccess.Transaction;
import RequestResult.ClearResult;

/**
 * Created by GrantRowberry on 2/15/17.
 */

public class ClearService {

    /**
     * Clears the database;
     * @return
     */
    public void clear() throws DatabaseException {
        Transaction transaction = new Transaction();
        transaction.openConnection();
        transaction.createTables();
        transaction.closeConnection(true);
    }

}
