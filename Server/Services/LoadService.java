package Services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import DataAccess.DatabaseException;
import DataAccess.EventDao;
import DataAccess.PersonDao;
import DataAccess.Transaction;
import DataAccess.UserDao;
import Model.Event;
import Model.Person;
import Model.User;
import RequestResult.LoadRequest;
import RequestResult.LoadResult;

/**
 * Created by GrantRowberry on 2/15/17.
 */

public class LoadService {

    /**
     * Communicates with the appropriate DAOs to load the database.
     * @param request
     * @return
     */

     public LoadResult load(LoadRequest request) throws DatabaseException {
        Transaction transaction = new Transaction();
         try {
             transaction.openConnection();
             transaction.createTables();
             UserDao ud = transaction.getUd();
             PersonDao pd = transaction.getPd();
             EventDao ed = transaction.getEd();
             List<User> users = new ArrayList<>(Arrays.asList(request.getUsers()));
             ud.insertUsers(users);
             List<Person> persons = new ArrayList<>(Arrays.asList(request.getPersons()));
             pd.insertPersons(persons);
             List<Event> events = new ArrayList<>(Arrays.asList(request.getEvents()));
             ed.insertEvents(events);
             transaction.closeConnection(true);

             LoadResult result = new LoadResult();
             result.setMessage("Successfully added " + request.getUsers().length + " users, " + request.getPersons().length + " persons, and " + request.getEvents().length + " events to the database.");
             return result;
         } catch (DatabaseException db){
             transaction.closeConnection(false);
             throw db;
         }
    }
}
