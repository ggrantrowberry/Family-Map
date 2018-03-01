package DataAccess;


import java.util.ArrayList;
import java.util.List;

import Model.AuthToken;
import Model.Event;
import Model.Person;
import Model.User;

import static org.junit.Assert.*;

/**
 * Created by GrantRowberry on 2/28/17.
 */
public class PersonDaoTest {
    Transaction transaction;
    Person p;
    PersonDao pd;
    AuthTokenDao ad;
    UserDao ud;

    @org.junit.Before
    public void setUp() throws Exception {
        transaction = new Transaction();
        transaction.openConnection();
        transaction.createTables();
        pd = transaction.getPd();
        ad = transaction.getAd();
        ud = transaction.getUd();
    }

    @org.junit.After
    public void tearDown() throws Exception {
        transaction.closeConnection(true);
    }


    public Person getPerson(String eventID) throws Exception {
        return pd.getPerson(eventID);

    }

    @org.junit.Test
    public void insertPerson() throws Exception {
        User u = new User();
        u.setUsername("g$row");
        u.setPassword("g$row");
        u.setFirstName("Grant");
        u.setLastName("Rowberry");
        u.setGender("m");
        u.setEmail("g@gmail.com");
        u.setPersonID("1234abcd");
        ud.insertUser(u);

        p = new Person();

        p.setPersonID("12345");
        p.setDescendant("g$row");
        p.setFirstName("Jimbo");
        p.setLastName("Bimbo");
        p.setGender("m");
        p.setFather("578ebe");
        p.setMother("7463ed");
        p.setSpouse("5p0u53");
        pd.insertPerson(p);
        Person two = getPerson("12345");
        assertEquals(p.getPersonID(),two.getPersonID());
        assertEquals(p.getDescendant(),two.getDescendant());

    }


    public List<Person> getPersons(AuthToken token) throws Exception {
        return pd.getPersons(token);
    }

    @org.junit.Test
    public void insertPersons() throws Exception {
        User u = new User();
        u.setUsername("g$row");
        u.setPassword("g$row");
        u.setFirstName("Grant");
        u.setLastName("Rowberry");
        u.setGender("m");
        u.setEmail("g@gmail.com");
        u.setPersonID("1234abcd");
        ud.insertUser(u);

        AuthToken at = new AuthToken();
        at.setUsername("g$row");
        ad.insertAuthToken(at);
        p = new Person();
        //User to compare to.
        p.setPersonID("12345");
        p.setDescendant("g$row");
        p.setFirstName("Jimbo");
        p.setLastName("Bimbo");
        p.setGender("m");
        p.setFather("578ebe");
        p.setMother("7463ed");
        p.setSpouse("5p0u53");
        Person a = new Person();
        a.setPersonID("54321");
        a.setDescendant("g$row");
        a.setFirstName("Kimbo");
        a.setLastName("Bimbo");
        a.setGender("f");
        a.setFather("578ebe");
        a.setMother("7463ed");
        a.setSpouse("5p0u53");


        List<Person> persons = new ArrayList<>();
        persons.add(p);
        persons.add(a);
        pd.insertPersons(persons);
        List<Person> personsToCompare = getPersons(at);
        //These will be compared to the previously created events to make sure the insertEvents and getEvents works
        Person one = personsToCompare.get(0);
        Person two = personsToCompare.get(1);
        assertEquals(p.getPersonID(), one.getPersonID());
        assertEquals(p.getDescendant(), one.getDescendant());
        assertEquals(a.getPersonID(), two.getPersonID());
        assertEquals(a.getDescendant(), two.getDescendant());

    }

    @org.junit.Test
    public void deletePersons() throws Exception {
        //Test functionality of cascading deletes in sqlite.
        //Inserts a user into the user table.

        User u = new User();
        u.setUsername("g$row");
        u.setPassword("g$row");
        u.setFirstName("Grant");
        u.setLastName("Rowberry");
        u.setGender("m");
        u.setEmail("g@gmail.com");
        u.setPersonID("1234abcd");
        ud.insertUser(u);

        //Inserts events in the table
        p = new Person();
        p.setPersonID("12345");
        p.setDescendant("g$row");
        p.setFirstName("Jimbo");
        p.setLastName("Bimbo");
        p.setGender("m");
        p.setFather("578ebe");
        p.setMother("7463ed");
        p.setSpouse("5p0u53");
        pd.insertPerson(p);
        Person a = new Person();
        a.setPersonID("54321");
        a.setDescendant("g$row");
        a.setFirstName("Kimbo");
        a.setLastName("Bimbo");
        a.setGender("f");
        a.setFather("578ebe");
        a.setMother("7463ed");
        a.setSpouse("5p0u53");
        pd.insertPerson(a);

        ud.deleteUser(u.getUsername());
        Person test = pd.getPerson("12345");
        // System.out.println(test.getDescendant());

        //ed.deleteEve("grantrow");
        if(pd.getPerson("12345") == null){
            assertTrue(true);
        } else {
            assertTrue(false);
        }


    }

}