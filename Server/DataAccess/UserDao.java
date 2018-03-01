package DataAccess;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Model.User;
import RequestResult.LoginResult;

/**
 * Created by GrantRowberry on 2/13/17.
 */

public class UserDao {
    Connection conn;

    public Connection getConn() {
        return conn;
    }

    public void setConn(Connection conn) {
        this.conn = conn;
    }

    /**
     * This accesses the database and selects the desired information and returns a user object.
     * @return
     */
    public User getUser(String username) throws DatabaseException {
        User u = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            String sql = "select username, password, email, firstName, lastName, gender, userID" +
                    " from users " +
                    "where username = '"+ username + "'";


            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();

            //Checks to see if the query actually gets anything from the database
            while(rs.next()){
                u = new User();
                String newUsername = rs.getString(1);
                String password = rs.getString(2);
                String email = rs.getString(3);
                String firstName = rs.getString(4);
                String lastName = rs.getString(5);
                String gender = rs.getString(6);
                String userID = rs.getString(7);
                u.setUsername(newUsername);
                u.setPassword(password);
                u.setEmail(email);
                u.setFirstName(firstName);
                u.setLastName(lastName);
                u.setGender(gender);
                u.setPersonID(userID);
            }



        }
        catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseException("Get user failed.");

        } finally {
            try{
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
//                if(u.getUsername() == null)
//                {
//                   return null;
//                }
                return u;

            } catch(SQLException e){
                e.printStackTrace();
            }

        }
        return u;
    }



    /**
     * This takes a user object and inserts it into the database.
     * @param u
     */

    public void insertUser(User u) throws DatabaseException{
        PreparedStatement stmt = null;

        if(doesUserExist(u.getUsername())){
            throw new DatabaseException("User already exists");
        }


        try {
            String sql = "insert into users (username, password, email, firstName, lastName, gender, userID) " +
                    "values( ?,  ?, ?,  ?, ?, ?, ?) ";
            stmt = conn.prepareStatement(sql);
            //
            stmt.setString(1, u.getUsername());
            stmt.setString(2, u.getPassword());
            stmt.setString(3, u.getEmail());
            stmt.setString(4, u.getFirstName());
            stmt.setString(5, u.getLastName());
            stmt.setString(6, u.getGender());
            stmt.setString(7, u.getPersonID());
            if (stmt.executeUpdate() != 1) {
                throw new DatabaseException("Error inserting user.");
            } else {
               // System.out.println("YEah buddy");

            }
        }
        catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseException("Insert User Failed");
        }finally {
            try {
                if (stmt != null) stmt.close();
            } catch(SQLException e){
                e.printStackTrace();
            }
        }



    }

    /**
     * Gets all the users
     * @return
     */

    public List<User> getUsers(){
        List<User> users = new ArrayList<User>();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {

            String sql = "Select username, password, email, firstName, lastName, gender, userID from users";
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();

            while(rs.next()) {
                User u = new User();
                u.setUsername(rs.getString(1));
                u.setPassword(rs.getString(2));
                u.setEmail(rs.getString(3));
                u.setFirstName(rs.getString(4));
                u.setLastName(rs.getString(5));
                u.setGender(rs.getString(6));
                u.setPersonID(rs.getString(7));
                users.add(u);
            }
            if(!rs.next()){
                return null;
            }

        }
        catch (SQLException e) {
            e.printStackTrace();

        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                return users;
            }
            catch(SQLException e){
                e.printStackTrace();
            }
        }

        return null;
    }

    /**
     * Inserts users into the table
     * @param users
     */

    public void insertUsers(List<User> users) throws DatabaseException{
        System.out.println(users.size());
        for (User u: users) {
            insertUser(u);
        }
    }

    /**
     * Deletes the user table
     */

    public void deleteUserTable() throws DatabaseException{
        PreparedStatement stmt = null;
        try {

                String sql = "drop table if exists users ";
                stmt = conn.prepareStatement(sql);


                if (stmt.execute()) {
                    // OK
                } else {
                    throw new DatabaseException("Error inserting users.");
                }
            } catch (SQLException e) {
            // ERROR
        }finally {
            try {
                if (stmt != null) stmt.close();
            } catch(SQLException e){
                e.printStackTrace();
            }
        }

    }

    public void deleteUser(String username) throws DatabaseException{
        PreparedStatement stmt = null;
        try{
            String sql = "Delete from users where username = '" + username + "'";
            stmt = conn.prepareStatement(sql);
            if (stmt.executeUpdate() != 1) {
                throw new DatabaseException("Error deleting user.");
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




    /**
     * Makes sure that the user and password are in the database
     * @param username
     * @param password
     * @return
     */

    public boolean validateUser(String username, String password) throws DatabaseException {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try{
            String sql = "select username, password from users where username = '" + username + "' and password = '" + password + "'";
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();
            if(rs.next()){
                return true;
            } else {
                return false;
            }


        } catch(SQLException e){
            e.printStackTrace();
           throw new DatabaseException("Username or password not correct.");
        }

    }

    boolean doesUserExist(String username) throws DatabaseException {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try{
            String sql = "select username from users where username = '" + username + "'";
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();
            if(rs.next()){
                return true;
            } else {
                return false;
            }
        } catch(SQLException e){
            e.printStackTrace();
            throw new DatabaseException("Error finding user");
        }
    }


}
