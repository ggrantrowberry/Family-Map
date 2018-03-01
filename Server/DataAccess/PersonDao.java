package DataAccess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Model.*;


/**
 * Created by GrantRowberry on 2/13/17.
 */

public class PersonDao {

    Connection conn;

    public Connection getConn() {
        return conn;
    }

    public void setConn(Connection conn) {
        this.conn = conn;
    }

    /**
     * Goes to the database and selects the information for the person matching the person id.
     * Returns that information packaged in a person object.
     * @param personID
     * @return
     */
    public Person getPerson(String personID) throws DatabaseException{
        Person p = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            String sql = "select personID, descendant, firstName, lastName, gender, father, mother, spouse" +
                    " from persons " +
                    "where personID = '"+ personID + "'";


            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();

            //Checks to see if the query actually gets anything from the database
            while(rs.next()){
                p = new Person();
                p.setPersonID(rs.getString(1));
                p.setDescendant(rs.getString(2));
                p.setFirstName(rs.getString(3));
                p.setLastName(rs.getString(4));
                p.setGender(rs.getString(5));
                p.setFather(rs.getString(6));
                p.setMother(rs.getString(6));
                p.setSpouse(rs.getString(7));

            }



        }
        catch (SQLException exc) {
            exc.printStackTrace();
            throw new DatabaseException("Get Person failed.");

        } finally {
            try{
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();

                return p;

            } catch(SQLException exc){
                exc.printStackTrace();
            }

        }
        return p;
    }

    /**
     * Goes to the database and selects all the information for all the people in the AuthToken holders family.
     * Returns the information in a Person array.
     *
     * @return
     */
    public List<Person> getPersons(AuthToken token) throws DatabaseException{
        List<Person> persons = new ArrayList<>();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {

            String sql = "select persons.personId, persons.descendant, persons.firstName," +
                    " persons.lastName, persons.gender, persons.spouse, persons.father, persons.mother " +
                    "from persons, authTokens where authTokens.username = persons.descendant and authTokens.authToken = '"
                    + token.getAuthToken() + "'";
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();

            while(rs.next()) {
                Person p = new Person();
                p.setPersonID(rs.getString(1));
                p.setDescendant(rs.getString(2));
                p.setFirstName(rs.getString(3));
                p.setLastName(rs.getString(4));
                p.setGender(rs.getString(5));
                p.setSpouse(rs.getString(6));
                p.setFather(rs.getString(7));
                p.setMother(rs.getString(8));


                persons.add(p);
            }
            if(!rs.next()){
                return null;
            }

        }
        catch (SQLException exc) {
            exc.printStackTrace();
            throw new DatabaseException("Error getting persons.");

        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                return persons;
            }
            catch(SQLException exc){
                exc.printStackTrace();
            }
        }

        return null;    }

    /**
     * Adds a person to the database
     * @param p
     */
    public void insertPerson(Person p) throws DatabaseException{
        PreparedStatement stmt = null;
        try {
            String sql = "insert into persons (personID, descendant, firstName, lastName, gender, father, mother, spouse) " +
                    "values( ?,  ?, ?,  ?, ?, ?, ?, ?) ";
            stmt = conn.prepareStatement(sql);
            //
            stmt.setString(1, p.getPersonID());
            stmt.setString(2, p.getDescendant());
            stmt.setString(3, p.getFirstName());
            stmt.setString(4, p.getLastName());
            stmt.setString(5, p.getGender());
            stmt.setString(6, p.getFather());
            stmt.setString(7, p.getMother());
            stmt.setString(8, p.getSpouse());

            if (stmt.executeUpdate() != 1) {
                throw new DatabaseException("Error inserting person.");
            } else {
                // System.out.println("YEah buddy");

            }
        }
        catch (SQLException exc) {
            exc.printStackTrace();
            throw new DatabaseException("Insert person failed.");
        }finally {
            try {
                if (stmt != null) stmt.close();
            } catch(SQLException exc){
                exc.printStackTrace();
            }
        }
    }

    /**
     * Adds persons to the database
     * @param persons
     */
    public void insertPersons(List<Person> persons) throws DatabaseException {
        for (Person person: persons){
            insertPerson(person);
        }
    }



    /**
     * Deletes a single person
     * @param personId
     */

    public void deletePerson(String personId){

    }

    /**
     * Deletes all the persons related to a user
     * @param username
     */

    public void deletePersons(String username) throws DatabaseException{
        PreparedStatement stmt = null;
        try{
            String sql = "Delete from persons where descendant = '" + username + "'";
            stmt = conn.prepareStatement(sql);
            if (stmt.executeUpdate() != 1) {
                throw new DatabaseException("Error deleting person.");
            }

        } catch (SQLException e){
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null) stmt.close();
            } catch(SQLException e){
                e.printStackTrace();
            }
        }
    }

}
