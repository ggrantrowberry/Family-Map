package DataAccess;


import java.util.ArrayList;
import java.util.List;

import Model.AuthToken;
import Model.User;

import static org.junit.Assert.*;

/**
 * Created by GrantRowberry on 2/28/17.
 */
public class UserDaoTest {
    Transaction transaction;
    User u;
    UserDao ud;
    AuthToken testToken = new AuthToken();

    @org.junit.Before
    public void setUp() throws Exception {
        transaction = new Transaction();
        transaction.openConnection();
        transaction.createTables();
        ud = transaction.getUd();

    }
    @org.junit.After
    public void tearDown() throws Exception {
        transaction.closeConnection(true);
    }


    public User getUser(String username) throws Exception {
        return ud.getUser(username);

    }

    @org.junit.Test
    public void insertUser() throws Exception {
        u = new User();
        u.setUsername("grantrow");
        u.setPassword("g$row");
        u.setFirstName("Grant");
        u.setLastName("Rowberry");
        u.setGender("m");
        u.setEmail("g@gmail.com");
        u.setPersonID("1234abcd");
        //ad.insertAuthToken(testToken, "grantrow");
        ud.insertUser(u);
        User two = getUser("grantrow");
        assertEquals(u.getUsername(),two.getUsername());
        assertEquals(u.getPassword(),two.getPassword());

    }


    public List<User> getUsers() throws Exception {
        return ud.getUsers();
    }

    @org.junit.Test
    public void insertUsers() throws Exception {
        u = new User();
        //User to compare to.
        u.setUsername("grantrow");
        u.setPassword("g$row");
        u.setFirstName("Grant");
        u.setLastName("Rowberry");
        u.setGender("m");
        u.setEmail("g@gmail.com");
        u.setPersonID("1234abcd");
        User a = new User();
        a.setUsername("Jacobp");
        a.setPassword("jp");
        a.setFirstName("Jacob");
        a.setLastName("Pettingill");
        a.setGender("m");
        a.setEmail("jp@gmail.com");
        a.setPersonID("pqrs");


        List<User> users = new ArrayList<>();
        users.add(u);
        users.add(a);
        ud.insertUsers(users);
        List<User> usersToCompare = getUsers();
        System.out.println(usersToCompare.size());
        //These will be compared to the previously created users to make sure the insertUsers and getUsers works
        User one = usersToCompare.get(0);
        User two = usersToCompare.get(1);
        assertEquals(u.getUsername(), one.getUsername());
        assertEquals(u.getPassword(), one.getPassword());
        assertEquals(a.getUsername(), two.getUsername());
        assertEquals(a.getPassword(), two.getPassword());

    }

    @org.junit.Test
    public void deleteUser() throws Exception {
        u = new User();
        u.setUsername("grantrow");
        u.setPassword("g$row");
        u.setFirstName("Grant");
        u.setLastName("Rowberry");
        u.setGender("m");
        u.setEmail("g@gmail.com");
        u.setPersonID("1234abcd");
        ud.insertUser(u);
        User test = ud.getUser("grantrow");
        System.out.println(test.getUsername());
        ud.deleteUser("grantrow");
        if(ud.getUser("grantrow") == null){
            assertTrue(true);
        } else {
            assertTrue(false);
        }


    }

    @org.junit.Test
    public void validateUser() throws Exception {
        u = new User();
        u.setUsername("grantrow");
        u.setPassword("g$row");
        u.setFirstName("Grant");
        u.setLastName("Rowberry");
        u.setGender("m");
        u.setEmail("g@gmail.com");
        u.setPersonID("1234abcd");
        ud.insertUser(u);
        assertTrue(ud.validateUser("grantrow", "g$row"));
        assertFalse(ud.validateUser("grantrow","grow"));
    }

}