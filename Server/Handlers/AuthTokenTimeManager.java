package Handlers;

import DataAccess.AuthTokenDao;
import DataAccess.DatabaseException;
import DataAccess.Transaction;
import Model.AuthToken;

/**
 * Created by GrantRowberry on 3/9/17.
 */

public class AuthTokenTimeManager {
    public void run(AuthToken authToken) throws DatabaseException{
        Transaction t = new Transaction();
        try {
            t.openConnection();
            AuthTokenDao ad = new AuthTokenDao();
            ad.updateAuthTokenTime(authToken);
            ad.deleteExpiredAuthToken();
            t.closeConnection(true);
        } catch(DatabaseException de){
            de.printStackTrace();
            System.out.println(de.getMessage());
            t.closeConnection(false);
        }
    }

}
