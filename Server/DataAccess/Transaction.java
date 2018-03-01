package DataAccess;


import org.sqlite.SQLiteConfig;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by GrantRowberry on 2/23/17.
 */

public class Transaction {
    private Connection conn;
    private UserDao ud = new UserDao();
    private PersonDao pd = new PersonDao();
    private EventDao ed = new EventDao();
    private AuthTokenDao ad = new AuthTokenDao();


    public Transaction(){
        runThis();
    }
    public UserDao getUd() {
        ud.setConn(conn);
        return ud;
    }

    public Connection getConn(){
        return conn;
    }

    public void setUd(UserDao ud) {
        this.ud = ud;
    }

    public PersonDao getPd() {
        pd.setConn(conn);
        return pd;
    }

    public void setPd(PersonDao pd) {
        this.pd = pd;
    }

    public EventDao getEd() {
        ed.setConn(conn);
        return ed;

    }

    public void setEd(EventDao ed) {
        this.ed = ed;
    }

    public AuthTokenDao getAd() {
        ad.setConn(conn);
        return ad;
    }

    public void setAd(AuthTokenDao ad) {
        this.ad = ad;
    }

    static void runThis(){
        try {
            final String driver = "org.sqlite.JDBC";
            Class.forName(driver);

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }




    public void openConnection() throws DatabaseException {
        try {
            final String CONNECTION_URL = "jdbc:sqlite:fmsdb.sqlite";

            SQLiteConfig config = new SQLiteConfig();
            config.enforceForeignKeys(true);

            conn = DriverManager.getConnection(CONNECTION_URL,config.toProperties());

            conn.setAutoCommit(false);
            Statement stmt = conn.createStatement();

        } catch(SQLException e){
            throw new DatabaseException("openConnection Failed",e);
        }

    }

    public void closeConnection(boolean commit) throws DatabaseException {
        try {
            if (commit) {
                conn.commit();
            } else {
                conn.rollback();
            }

            conn.close();
            conn = null;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseException("closeConnection failed", e);
        }
    }



    public void createTables() throws DatabaseException {
        try {
            Statement stmt = null;
            try {
                stmt = conn.createStatement();
                stmt.executeUpdate("PRAGMA foreign_keys = ON;\n");
                stmt.executeUpdate("drop table if exists users;" +
                        "drop table if exists persons;" +
                        "drop table if exists events;" +
                        "drop table if exists authTokens;");
                stmt.executeUpdate("create table users" +
                        "(" +
                        "    username text not null unique," +
                        "    password text not null," +
                        "    email text not null,\n" +
                        "    firstName text not null,\n" +
                        "    lastName text not null,\n" +
                        "    gender text not null,\n" +
                        "    userID text not null,\n" +
                        "    constraint ck_gender check (gender in ('m','f'))\n" +
                        ");\n" +
                        "\n" +
                        "create table persons(" +
                        "    personID text not null primary key unique,\n" +
                        "    descendant text not null, \n" +
                        "    firstName text not null,\n" +
                        "    lastName text not null,\n" +
                        "    gender text not null,\n" +
                        "    father text,\n" +
                        "    mother text,\n" +
                        "    spouse text,\n" +
                        "    constraint ck_gender check (gender in ('m','f'))\n" +
                        "    Foreign Key(descendant) references users(username) on delete cascade" +
                        ");\n" +
                        "\n" +
                        "\n" +
                        "create table events\n" +
                        "(\n" +
                        "    eventID text not null primary key unique,\n" +
                        "    descendant text not null,\n" +
                        "    personID text not null,\n" +
                        "    latitude real not null,\n" +
                        "    longitude real not null,\n" +
                        "    country text not null,\n" +
                        "    city text not null,\n" +
                        "    eventType text not null,\n" +
                        "    eventYear real not null,\n" +
                        //"    Foreign Key(personID) references persons(personID) on delete cascade" +
                        "    Foreign Key(descendant) references users(username) on delete cascade"+
                        ");\n" +
                        "\n" +
                        "\n" +
                        "create table authTokens\n" +
                        "(\n" +
                        "    authToken text not null,\n" +
                        "    username text not null,\n" +
                        "    tstamp datetime not null" +
                        //"    Foreign Key(username) references users(username) on delete cascade"+
                        ")");
            }
            finally {
                if (stmt != null) {
                    stmt.close();
                    stmt = null;
                }
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseException("createTables failed", e);
        }
    }

    public static void main(String[] args) {
        try {
            Transaction db = new Transaction();

            db.openConnection();
            db.createTables();
            db.closeConnection(true);

            System.out.println("OK");
        }
        catch (DatabaseException e) {
            e.printStackTrace();
        }
    }

}
