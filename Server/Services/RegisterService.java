package Services;

import java.util.UUID;

import DataAccess.DatabaseException;
import DataAccess.PersonDao;
import DataAccess.Transaction;
import DataAccess.UserDao;
import Model.AuthToken;
import Model.Person;
import Model.User;
import RequestResult.FillRequest;
import RequestResult.LoginRequest;
import RequestResult.LoginResult;
import RequestResult.RegisterRequest;
import RequestResult.RegisterResult;

/**
 * Created by GrantRowberry on 2/15/17.
 */

public class RegisterService {
    private int fillGenerations = 4;
    /**
     * Unpackages a register request to be able to communicate with the DAOs.
     * @param request
     * @return
     */

    //The request must have everything in it!
    public LoginResult register(RegisterRequest request) throws DatabaseException {

        if(!request.getGender().equals("m") && !request.getGender().equals("f")){
            throw new DatabaseException(request.getGender() + " is not a gender.");
        }

        System.out.println(request.getUsername());
        //Creates and populates a new user object
        User u = new User();
        u.setUsername(request.getUsername());
        u.setEmail(request.getEmail());
        u.setFirstName(request.getFirstName());
        u.setLastName(request.getLastName());
        u.setPassword(request.getPassword());
        u.setGender(request.getGender());
        String personID = UUID.randomUUID().toString();
        u.setPersonID(personID); //generates random string for a person ID


//        Person p = new Person();
//        p.setPersonID(u.getPersonID());
//        p.setFirstName(u.getFirstName());
//        p.setLastName(u.getLastName());
//        p.setDescendant(u.getUsername());
//        p.setGender(u.getGender());

        //Creates DAO's

        Transaction transaction = new Transaction();
        try {
            transaction.openConnection();

            UserDao ud = transaction.getUd();
            ud.insertUser(u);

//            PersonDao pd = transaction.getPd();
//            pd.insertPerson(p);
            transaction.closeConnection(true);
        } catch(DatabaseException db){
            transaction.closeConnection(false);
            throw db;
        }


        //Fills with generational data
        FillService f = new FillService();
        f.fill(request.getUsername());
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername(request.getUsername());
        loginRequest.setPassword(request.getPassword());


        LoginService ls = new LoginService();
        LoginResult result = ls.login(loginRequest);


        return result;



    }




}
