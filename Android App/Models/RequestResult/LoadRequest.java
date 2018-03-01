package com.example.grantrowberry.fmclient.Models.RequestResult;

import com.example.grantrowberry.fmclient.Models.Model.*;


/**
 * Created by GrantRowberry on 2/15/17.
 */

public class LoadRequest {

    /**
     * Holds all the information necessary to load information in the database.
     *
     *
     *
     */

    /*
     The “users” property in the request body contains an array of users to be created.
     * The “persons” and “events” properties contain family history information for these users.
     * The objects contained in the “persons” and “events” arrays should be added to the server’s database.
     * The objects in the “users” array have the same format as those passed to the /user/register API.
     * The objects in the “persons” array have the same format as those returned by the /person/[personID] API.
     * The objects in the “events” array have the same format as those returned by the /event/[eventID] API.
     */


    private User[] users;
    private Person[] persons;
    private Event[] events;

    public User[] getUsers() {
        return users;
    }

    public void setUsers(User[] users) {
        this.users = users;
    }

    public Person[] getPersons() {
        return persons;
    }

    public void setPersons(Person[] persons) {
        this.persons = persons;
    }

    public Event[] getEvents() {
        return events;
    }

    public void setEvents(Event[] events) {
        this.events = events;
    }
}
