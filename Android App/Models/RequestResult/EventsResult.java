package com.example.grantrowberry.fmclient.Models.RequestResult;

import com.example.grantrowberry.fmclient.Models.Model.*;

import java.util.List;


/**
 * Created by GrantRowberry on 2/15/17.
 */

public class EventsResult {

    /**
     * Holds an array of events
     */
    List<Event> events;

    public List<Event> getEvents() {
        return events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }
}
