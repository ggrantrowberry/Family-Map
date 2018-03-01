package DataAccess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Model.AuthToken;
import Model.Event;

/**
 * Created by GrantRowberry on 2/13/17.
 */

public class EventDao {

    Connection conn;


    public Connection getConn() {
        return conn;
    }

    public void setConn(Connection conn) {
        this.conn = conn;
    }

    /**
     * Accesses the database and gets the event with the corresponding event Id.
     *
     * @param eventID
     * @return Event object
     */
    public Event getEvent(String eventID) throws DatabaseException{
        Event e = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            String sql = "select eventID, descendant, personID, latitude, longitude, country, city, eventType, eventYear" +
                    " from events " +
                    "where eventID = '"+ eventID + "' ";


            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();

            //Checks to see if the query actually gets anything from the database
            while(rs.next()){
                e = new Event();
                e.setEventID(rs.getString(1));
                e.setDescendant(rs.getString(2));
                e.setPersonID(rs.getString(3));
                e.setLatitude(rs.getDouble(4));
                e.setLongitude(rs.getDouble(5));
                e.setCountry(rs.getString(6));
                e.setCity(rs.getString(7));
                e.setEventType(rs.getString(8));
                e.setYear(rs.getInt(9));

            }



        }
        catch (SQLException exc) {
            exc.printStackTrace();
            throw new DatabaseException("Get Event failed.");

        } finally {
            try{
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
//                if(u.getUsername() == null)
//                {
//                   return null;
//                }
                return e;

            } catch(SQLException exc){
                exc.printStackTrace();
            }

        }
        return e;
    }

    /**
     * Accesses the database and gets all the events for the family of the authToken holder/
     *
     * @return
     */
    public List<Event> getEvents(AuthToken token){
        List<Event> events = new ArrayList<>();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {

            String sql = "select events.eventId, events.descendant, events.personID," +
                    " events.latitude, events.longitude, events.country, events.city, events.eventType, events.eventYear " +
                    "from events, authTokens where authTokens.username = events.descendant and authTokens.authToken = '"
                    + token.getAuthToken() + "'";
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();

            while(rs.next()) {
                Event e = new Event();
                e.setEventID(rs.getString(1));
                e.setDescendant(rs.getString(2));
                e.setPersonID(rs.getString(3));
                e.setLatitude(rs.getDouble(4));
                e.setLongitude(rs.getDouble(5));
                e.setCountry(rs.getString(6));
                e.setCity(rs.getString(7));
                e.setEventType(rs.getString(8));
                e.setYear(rs.getInt(9));
                events.add(e);
            }
            if(!rs.next()){
                return null;
            }

        }
        catch (SQLException exc) {
            exc.printStackTrace();

        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                return events;
            }
            catch(SQLException exc){
                exc.printStackTrace();
            }
        }

        return null;
    }

    /**
     * Inserts events into the table
     * @param events
     */
    public void insertEvents(List<Event> events) throws DatabaseException {
        for (Event e: events) {
            insertEvent(e);
        }


    }

    public void insertEvent(Event e) throws DatabaseException{
        PreparedStatement stmt = null;
        try {
            String sql = "insert into events (eventID, descendant, personID, latitude, longitude, country, city, eventType, eventYear) " +
                    "values( ?,  ?, ?,  ?, ?, ?, ?, ?, ?) ";
            stmt = conn.prepareStatement(sql);
            //
            stmt.setString(1, e.getEventID());
            stmt.setString(2, e.getDescendant());
            stmt.setString(3, e.getPersonID());
            stmt.setDouble(4, e.getLatitude());
            stmt.setDouble(5, e.getLongitude());
            stmt.setString(6, e.getCountry());
            stmt.setString(7, e.getCity());
            stmt.setString(8, e.getEventType());
            stmt.setInt(9, e.getYear());
            if (stmt.executeUpdate() != 1) {
                throw new DatabaseException("Error inserting event.");
            } else {
                // System.out.println("YEah buddy");

            }
        }
        catch (SQLException exc) {
            exc.printStackTrace();
            throw new DatabaseException("Insert Event Failed");
        }finally {
            try {
                if (stmt != null) stmt.close();
            } catch(SQLException exc){
                exc.printStackTrace();
            }
        }

    }

    /**
     * Drops the eventTable
     */
    public void deleteEventTable(){

    }

    /**
     * Deletes all events from the event table linked to a user
     */

    public void deleteEvents(String username) throws DatabaseException{
        PreparedStatement stmt = null;
        try{
            String sql = "Delete from events where descendant = '" + username + "'";
            stmt = conn.prepareStatement(sql);
            if (stmt.executeUpdate() != 1) {
                throw new DatabaseException("Error deleting event.");
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
