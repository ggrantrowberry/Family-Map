package DataAccess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import Model.AuthToken;

/**
 * Created by GrantRowberry on 2/13/17.
 */

public class AuthTokenDao {

    Connection conn;

    public Connection getConn() {
        return conn;
    }

    public void setConn(Connection conn) {
        this.conn = conn;
    }

    /**
     * Goes to the database and gets the authToken for the corresponding userId.
     *
     * @param username
     * @return
     */
    public AuthToken getAuthToken(String username) throws DatabaseException{
        AuthToken a = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            String sql = "select authToken, username, tstamp" +
                    " from authTokens " +
                    "where username = '"+ username + "'";


            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();

            //Checks to see if the query actually gets anything from the database
            while(rs.next()){
                a = new AuthToken();
                a.setAuthToken(rs.getString(1));
                a.setUsername(rs.getString(2));
                a.setTimestamp(rs.getString(3));
            }



        }
        catch (SQLException exc) {
            exc.printStackTrace();
            throw new DatabaseException("Get Authtoken failed.");

        } finally {
            try{
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();

                return a;

            } catch(SQLException exc){
                exc.printStackTrace();
            }

        }
        return a;
    }


    public AuthToken existsAuthToken(String authToken) throws DatabaseException{
        AuthToken a = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            String sql = "select authToken, username, tstamp" +
                    " from authTokens " +
                    "where authToken = '"+ authToken + "'";


            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();

            //Checks to see if the query actually gets anything from the database
            while(rs.next()){
                a = new AuthToken();
                a.setAuthToken(rs.getString(1));
                a.setUsername(rs.getString(2));
                a.setTimestamp(rs.getString(3));
            }



        }
        catch (SQLException exc) {
            exc.printStackTrace();
            throw new DatabaseException("Invalid AuthToken.");

        } finally {
            try{
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();

                return a;

            } catch(SQLException exc){
                exc.printStackTrace();
            }

        }
        return a;
    }

    /**
     * Inserts an authtoken into the database.
     * @param a
     */
    public void insertAuthToken(AuthToken a) throws DatabaseException{
        PreparedStatement stmt = null;

        try {
            String sql = "insert into authTokens (authToken, username, tstamp) " +
                    "values( ?, ?, datetime('now')) ";
            System.out.println(conn);
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, a.getAuthToken());
            stmt.setString(2, a.getUsername());

            if (stmt.executeUpdate() == 1) {
                // OK
            } else {
                throw new DatabaseException("Error inserting authToken.");
            }
        }
        catch (SQLException e) {
            throw new DatabaseException("Error inserting authToken.");
        }finally {
            try {
                if (stmt != null) stmt.close();
            } catch(SQLException e){
                e.printStackTrace();
            }
        }
    }


    /**
     * Drops the AuthToken table.
     */
    public void deleteAuthTokenTable(){

    }
    public void updateAuthTokenTime(AuthToken a) throws DatabaseException{
        PreparedStatement stmt = null;

        try {
            String sql = "update authTokens set tstamp = datetime('now') " +
                    "where authToken = '" + a.getAuthToken() + "'";
            stmt = conn.prepareStatement(sql);

            if (stmt.executeUpdate() == 1) {
                // OK
            } else {
                throw new DatabaseException("Error updating authToken timestamp.");
            }
        }
        catch (SQLException e) {
            // ERROR
        }finally {
            try {
                if (stmt != null) stmt.close();
            } catch(SQLException e){
                e.printStackTrace();
            }
        }

    }
    /**
     * Deletes authTokens that are expired
     */
    //TODO: implement this.

    public void deleteExpiredAuthToken() throws DatabaseException{
        PreparedStatement stmt = null;
        try {
            String sql = "DELETE FROM authTokens WHERE tstamp <= datetime('now','-1 hour')";
            stmt = conn.prepareStatement(sql);

            if (stmt.executeUpdate() == 1) {
                // OK
            } else {
                throw new DatabaseException("Error deleting expired authToken.");
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseException("Error deleting expired authToken.");
        }finally {
            try {
                if (stmt != null) stmt.close();
            } catch(SQLException e){
                e.printStackTrace();
            }
        }


    }


}
