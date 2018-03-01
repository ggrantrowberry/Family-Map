package Services;

import DataAccess.AuthTokenDao;
import DataAccess.DatabaseException;
import DataAccess.Transaction;
import DataAccess.UserDao;
import Model.AuthToken;
import Model.User;
import RequestResult.LoginRequest;
import RequestResult.LoginResult;

/**
 * Created by GrantRowberry on 2/15/17.
 */

public class LoginService {

    /**
     * Communicates with the appropriate DAOs to login a user.
     * @param request
     * @return
     */

    public LoginResult login(LoginRequest request)throws DatabaseException{

        Transaction transaction = new Transaction();
        try {
            transaction.openConnection();
            AuthTokenDao ad = transaction.getAd();
            UserDao ud = transaction.getUd();
            if (!ud.validateUser(request.getUsername(), request.getPassword())) {
                throw new DatabaseException("Username or password is incorrect");
            }
            User u = ud.getUser(request.getUsername());
            AuthToken a = new AuthToken();
            a.setUsername(request.getUsername());
            ad.insertAuthToken(a);

            LoginResult result = new LoginResult();
            result.setUsername(request.getUsername());
            result.setPersonID(u.getPersonID());
            result.setAuthToken(a.getAuthToken());
            transaction.closeConnection(true);
            return result;

        } catch(DatabaseException db){
            transaction.closeConnection(false);
            throw db;
        }

    }


}
