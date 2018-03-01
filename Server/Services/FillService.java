package Services;

import java.io.FileNotFoundException;
import java.util.List;

import DataAccess.DatabaseException;
import DataAccess.EventDao;
import DataAccess.PersonDao;
import DataAccess.Transaction;
import DataAccess.UserDao;
import DataGeneration.FamilyTree;
import Model.AuthToken;
import Model.Event;
import Model.Person;
import Model.User;
import RequestResult.FillRequest;
import RequestResult.FillResult;
import RequestResult.LoginResult;

/**
 * Created by GrantRowberry on 2/15/17.
 */

public class FillService {
    /**
     * Communicates with the appropriate DAOs to fill from the database.
     * @param username,generations
     * @return
     */

    public FillResult fillWithGenerations(String username, int generations) throws DatabaseException{
        Transaction transaction = new Transaction();
        try {
            transaction.openConnection();
            UserDao ud = transaction.getUd();
            User user = ud.getUser(username);
            ud.deleteUser(username);
            ud.insertUser(user);

            PersonDao pd = transaction.getPd();
            pd.insertPerson(convertUserToPerson(user));
            Person p = pd.getPerson(user.getPersonID());

            EventDao ed = transaction.getEd();

            FamilyTree ft = new FamilyTree();
            ft.setGenerations(generations);
            ft.addGenerations(p);
            List<Event> events = ft.getEvents();
            List<Person> persons = ft.getPerson();

            pd.insertPersons(persons);
            ed.insertEvents(events);

            FillResult result = new FillResult();
            result.setMessage("Successfully added " + persons.size() + " and " + events.size() + " to the database");
            transaction.closeConnection(true);
            return result;

        } catch(DatabaseException db){
            transaction.closeConnection(false);
            throw db;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public FillResult fill(String username) throws DatabaseException{
        Transaction transaction = new Transaction();
        try {
            transaction.openConnection();
            UserDao ud = transaction.getUd();
            User user = ud.getUser(username);
            ud.deleteUser(username);
            ud.insertUser(user);

            PersonDao pd = transaction.getPd();
//            pd.insertPerson(convertUserToPerson(user));
//            Person p = pd.getPerson(user.getPersonID());

            EventDao ed = transaction.getEd();

            FamilyTree ft = new FamilyTree();

            ft.addGenerations(convertUserToPerson(user));
            List<Event> events = ft.getEvents();
            List<Person> persons = ft.getPerson();

            pd.insertPersons(persons);
            ed.insertEvents(events);


            FillResult result = new FillResult();
            result.setMessage("Successfully added " + (persons.size()+ 1) + "persons and " + events.size() + "events to the database");
            transaction.closeConnection(true);
            return result;

        } catch(DatabaseException db){
            transaction.closeConnection(false);
            throw db;
        } catch (FileNotFoundException e) {
            transaction.closeConnection(false);
            e.printStackTrace();
        }
        return null;
    }

    Person convertUserToPerson(User u){
        Person person = new Person();
        person.setDescendant(u.getUsername());
        person.setFirstName(u.getFirstName());
        person.setLastName(u.getLastName());
        person.setGender(u.getGender());
        person.setPersonID(u.getPersonID());
        return person;
    }
}
