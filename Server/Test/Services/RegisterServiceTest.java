package Services;

import DataAccess.DatabaseException;
import DataAccess.Transaction;
import DataAccess.UserDao;
import Model.AuthToken;
import Model.User;
import RequestResult.LoginResult;
import RequestResult.RegisterRequest;
import RequestResult.RegisterResult;
import static org.junit.Assert.*;

/**
 * Created by GrantRowberry on 3/6/17.
 */

public class RegisterServiceTest {
    Transaction transaction;
    User u;
    UserDao ud;

    @org.junit.Before
    public void setUp() throws Exception {
        transaction = new Transaction();
        transaction.openConnection();
        transaction.createTables();
        //ud = transaction.getUd();
        transaction.closeConnection(true);
    }
    @org.junit.After
    public void tearDown() throws Exception {
       // transaction.closeConnection(true);
    }

    @org.junit.Test
    public void register() throws DatabaseException {
        RegisterService rs = new RegisterService();
        RegisterRequest request = new RegisterRequest();
        request.setUsername("g$row");
        request.setEmail("g@g.com");
        request.setFirstName("Grant");
        request.setLastName("Rowberry");
        request.setPassword("password");
        request.setGender("Male");
        RegisterResult result = new RegisterResult();
        result.setUsername("g$row");

        LoginResult testResult = rs.register(request);
        assertEquals(result.getUsername(),testResult.getUsername());





    }

}
