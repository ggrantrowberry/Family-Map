package Services;

import java.util.List;

import DataAccess.DatabaseException;
import DataAccess.PersonDao;
import DataAccess.Transaction;
import Model.AuthToken;
import Model.Person;
import RequestResult.PersonsResult;

/**
 * Created by GrantRowberry on 2/15/17.
 */

public class PersonService {

    /**
     * Communicates with the appropriate DAOs to retrieve person information and return a PersonResult.
     * @param personID
     * @return
     */
    public Person getPerson(String personID, String username) throws DatabaseException{
        Transaction transaction = new Transaction();
        try{
            transaction.openConnection();
            PersonDao pd = transaction.getPd();
            Person p =  pd.getPerson(personID);
            if(!p.getDescendant().equals(username)){

                throw new DatabaseException("You don't have permission to access it");
            }
            transaction.closeConnection(true);
            return p;
        } catch (DatabaseException db){
          transaction.closeConnection(false);
         throw db;
        }

    }

    public PersonsResult getPersons(AuthToken token) throws DatabaseException{
        Transaction transaction = new Transaction();
        try{
            PersonsResult pr = new PersonsResult();
            transaction.openConnection();
            PersonDao pd = transaction.getPd();
            List<Person> persons;
            persons = pd.getPersons(token);
            pr.setData(persons);
            transaction.closeConnection(true);
            return pr;
        } catch (DatabaseException db){
            transaction.closeConnection(false);
            throw db;
        }

    }
}
