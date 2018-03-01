package DataAccess;


import java.util.ArrayList;
import java.util.List;

import Model.AuthToken;
import Model.Event;
import Model.User;

import static org.junit.Assert.*;

/**
 * Created by GrantRowberry on 2/28/17.
 */
public class EventDaoTest {
    Transaction transaction;
    Event e;
    EventDao ed;
    AuthTokenDao ad;
    UserDao ud;

    @org.junit.Before
    public void setUp() throws Exception {
        transaction = new Transaction();
        transaction.openConnection();
        transaction.createTables();
        ed = transaction.getEd();
        ad = transaction.getAd();
        ud = transaction.getUd();
    }

    @org.junit.After
    public void tearDown() throws Exception {
        transaction.closeConnection(true);
    }


    public Event getEvent(String eventID) throws Exception {
        return ed.getEvent(eventID);

    }

    @org.junit.Test
    public void insertEvent() throws Exception {
        User u = new User();
        u.setUsername("g$row");
        u.setPassword("g$row");
        u.setFirstName("Grant");
        u.setLastName("Rowberry");
        u.setGender("m");
        u.setEmail("g@gmail.com");
        u.setPersonID("1234abcd");
        ud.insertUser(u);

        e = new Event();

        e.setEventID("12345");
        e.setDescendant("g$row");
        e.setPersonID("9876");
        e.setLatitude(12.123);
        e.setLongitude(10.123);
        e.setCountry("USA");
        e.setCity("Highland");
        e.setEventType("marriage");
        e.setYear(2017);
        ed.insertEvent(e);
        Event two = getEvent("12345");
        assertEquals(e.getEventID(),two.getEventID());
        assertEquals(e.getDescendant(),two.getDescendant());

    }


    public List<Event> getEvents(AuthToken token) throws Exception {
        return ed.getEvents(token);
    }

    @org.junit.Test
    public void insertEvents() throws Exception {
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
        e = new Event();
        //User to compare to.
        e.setEventID("12345");
        e.setDescendant("g$row");
        e.setPersonID("9876");
        e.setLatitude(12.123);
        e.setLongitude(10.123);
        e.setCountry("USA");
        e.setCity("Highland");
        e.setEventType("marriage");
        e.setYear(2017);
        Event a = new Event();
        a.setEventID("54321");
        a.setDescendant("g$row");
        a.setPersonID("1234");
        a.setLatitude(10.123);
        a.setLongitude(12.123);
        a.setCountry("China");
        a.setCity("Beijing");
        a.setEventType("birth");
        a.setYear(1990);


        List<Event> events = new ArrayList<>();
        events.add(e);
        events.add(a);
        ed.insertEvents(events);
        List<Event> eventsToCompare = getEvents(at);
        //These will be compared to the previously created events to make sure the insertEvents and getEvents works
        Event one = eventsToCompare.get(0);
        Event two = eventsToCompare.get(1);
        assertEquals(e.getEventID(), one.getEventID());
        assertEquals(e.getDescendant(), one.getDescendant());
        assertEquals(a.getEventID(), two.getEventID());
        assertEquals(a.getDescendant(), two.getDescendant());

    }

    @org.junit.Test
    public void deleteEvents() throws Exception {
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
        e = new Event();
        e.setEventID("12345");
        e.setDescendant("g$row");
        e.setPersonID("9876");
        e.setLatitude(12.123);
        e.setLongitude(10.123);
        e.setCountry("USA");
        e.setCity("Highland");
        e.setEventType("marriage");
        e.setYear(2017);
        ed.insertEvent(e);
        Event a = new Event();
        a.setEventID("54321");
        a.setDescendant("g$row");
        a.setPersonID("1234");
        a.setLatitude(10.123);
        a.setLongitude(12.123);
        a.setCountry("China");
        a.setCity("Beijing");
        a.setEventType("birth");
        a.setYear(1990);
        ed.insertEvent(a);

        ud.deleteUser(u.getUsername());
        Event test = ed.getEvent("12345");
       // System.out.println(test.getDescendant());

        //ed.deleteEve("grantrow");
        if(ed.getEvent("12345") == null){
            assertTrue(true);
        } else {
            assertTrue(false);
        }
    }

}